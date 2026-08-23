package it.unicam.cs.mpgc.rpg126763.engine;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.*;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.ConditionFactory;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.DialogueEffect;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.EffectFactory;
import it.unicam.cs.mpgc.rpg126763.persistence.GameState;
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


    private GameState gameState;

    private final List<Nemico> enemyList;

    public GameEngine(GameUI ui, List<Nemico> enemyList, String filePath) {
        this.ui = ui;
        this.enemyList = enemyList;
        this.filePath = filePath;
        this.dialogueLoader = new DialogueLoader();
        this.effectFactory = new EffectFactory();
        this.conditionFactory = new ConditionFactory();
        this.saveManager = new JsonSaveManager(filePath);
        this.gameState = new GameState();
    }

    public void startNewGame(String playerName) {
        String finalName = (playerName == null || playerName.isBlank()) ? "Linden" : playerName;
        gameState.setPlayer(new Personaggio(finalName));
        gameState.setCurrentDialogueId("start");

        ui.showMessage("Nuova avventura per " + gameState.getPlayer().getName());
        ui.onGameStarted();
        System.out.println(gameState.toString());
        runCurrentDialogue();
    }

    public void loadGame() {
        try {
            this.gameState = saveManager.load();
            ui.showMessage("Partita caricata: " + gameState.getPlayer().getName());
            ui.onGameStarted();

            runCurrentDialogue();
        } catch (SaveLoadException e) {
            ui.showMessage("Nessun salvataggio valido (" + e.getMessage() + "). Creo nuova partita.");
            startNewGame("Linden");
        }
    }

    public void saveGame() {
        try {
            saveManager.save(gameState);

            ui.showMessage("Partita salvata.");
        } catch (SaveLoadException e) {
            ui.showMessage("Errore salvataggio: " + e.getMessage());
        }
    }

    private void runCurrentDialogue() {
        Map<String, Dialogue> dialogues = dialogueLoader.loadFromResource("dialogues.json");
        DialoguePlayer dialoguePlayer = new DialoguePlayer(dialogues, ui, conditionFactory, gameState);

        dialoguePlayer.play(gameState.getCurrentDialogueId())
                .thenCompose(result -> {
                    this.gameState.setCurrentDialogueId(result.lastDialogueId());
                    if (result.effectKey() != null) {
                        DialogueEffect effect = effectFactory.get(result.effectKey());
                        if (effect != null) {
                            effect.apply(gameState.getPlayer());
                            String desc = effect.getDescription();
                            if (desc != null) {
                                ui.showMessage(desc);
                            }
                        }
                    }

                    if (result.battle()) {
                        if (gameState.getEnemy()==null) {
                            gameState.setEnemy(enemyList.get(new Random().nextInt(enemyList.size())));
                        }
                        ui.showMessage("Un " + gameState.getEnemy().getName() + " appare! Preparati al combattimento!");
                        BattleManager battleManager = new BattleManager(ui);
                        return battleManager.startBattle(gameState);
                    } else {
                        return CompletableFuture.completedFuture(null);
                    }
                })
                .thenRun(() -> {
                    if (!"end".equals(gameState.getCurrentDialogueId())) {
                        try {
                            saveManager.save(gameState);
                            ui.showMessage("Partita salvata.");
                        } catch (SaveLoadException e) {
                            ui.showMessage("Errore salvataggio: " + e.getMessage());
                        }
                    } else {
                        ui.showMessage("La demo è finita. Grazie per aver giocato!");
                    }
                });
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}