package it.unicam.cs.mpgc.rpg126763.engine;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.*;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.ConditionFactory;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.DialogueEffect;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.EffectFactory;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveLoadException;
import it.unicam.cs.mpgc.rpg126763.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class GameEngine {

    private final GameUI ui;
    private final JsonSaveManager saveManager;
    private final DialogueLoader dialogueLoader;
    private final EffectFactory effectFactory;
    private final ConditionFactory conditionFactory;
    private final String filePath;

    private Personaggio player;
    private volatile String  currentDialogueId;
    private final List<Nemico> enemyList;
    private Nemico enemy;

    public GameEngine(GameUI ui, List<Nemico> enemyList, String filePath) {
        this.ui = ui;
        this.enemyList = enemyList;
        this.filePath = filePath;
        this.dialogueLoader = new DialogueLoader();
        this.effectFactory = new EffectFactory();
        this.conditionFactory = new ConditionFactory();
        this.saveManager = new JsonSaveManager(filePath);
    }

    public void startNewGame(String playerName) {
        String finalName = (playerName == null || playerName.isBlank()) ? "Linden" : playerName;
        player = new Personaggio(finalName);
        currentDialogueId = player.getDialogueId();

        ui.showMessage("Nuova avventura per " + player.getName());
        ui.onGameStarted();

        runCurrentDialogue();
    }

    public void loadGame() {
        try {
            SaveData data = saveManager.load();
            this.player = data.getPlayer();
            this.currentDialogueId = player.getDialogueId() != null ? player.getDialogueId() : "start";
            this.enemy = data.getEnemy();

            ui.showMessage("Partita caricata: " + player.getName());
            ui.onGameStarted();

            runCurrentDialogue();
        } catch (SaveLoadException e) {
            ui.showMessage("Nessun salvataggio valido (" + e.getMessage() + "). Creo nuova partita.");
            startNewGame("Linden");
        }
    }

    public void saveGame() {
        try {
            SaveData data = new SaveData(player, enemy, currentDialogueId);
            System.out.println("GameEngine: " + this.currentDialogueId);
            saveManager.save(data);

            ui.showMessage("Partita salvata.");
        } catch (SaveLoadException e) {
            ui.showMessage("Errore salvataggio: " + e.getMessage());
        }
    }

    private void runCurrentDialogue() {
        Map<String, Dialogue> dialogues = dialogueLoader.loadFromResource("dialogues.json");
        DialoguePlayer dialoguePlayer = new DialoguePlayer(dialogues, ui, conditionFactory, player);

        dialoguePlayer.play(currentDialogueId)
                .thenCompose(result -> {
                    this.currentDialogueId = result.lastDialogueId();
                    System.out.println("GameEngine2: " + this.currentDialogueId);
                    if (result.effectKey() != null) {
                        DialogueEffect effect = effectFactory.get(result.effectKey());
                        if (effect != null) {
                            effect.apply(player);
                            String desc = effect.getDescription();
                            if (desc != null) {
                                ui.showMessage(desc);
                            }
                        }
                    }

                    if (result.battle()) {
                        if (enemy==null) {
                            enemy = enemyList.get(new Random().nextInt(enemyList.size()));
                        }
                        ui.showMessage("Un " + enemy.getName() + " appare! Preparati al combattimento!");
                        BattleManager battleManager = new BattleManager(ui);
                        return battleManager.startBattle(player, enemy);
                    } else {
                        return CompletableFuture.completedFuture(null);
                    }
                })
                .thenRun(() -> {
                    if (!"end".equals(currentDialogueId)) {
                        try {
                            SaveData data = new SaveData(player, enemy, currentDialogueId);
                            saveManager.save(data);
                            ui.showMessage("Partita salvata.");
                        } catch (SaveLoadException e) {
                            ui.showMessage("Errore salvataggio: " + e.getMessage());
                        }
                    } else {
                        ui.showMessage("La demo è finita. Grazie per aver giocato!");
                    }
                });
    }
}