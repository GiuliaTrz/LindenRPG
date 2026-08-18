package it.unicam.cs.mpgc.rpg126763.engine;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.*;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.DialogueEffect;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.EffectFactory;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveLoadException;
import it.unicam.cs.mpgc.rpg126763.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Coordina il flusso di gioco: dialoghi, combattimenti, salvataggi.
 * Dipende da GameUI per l'interazione con l'utente.
 */
public class GameEngine {

    private final GameUI ui;
    private final JsonSaveManager saveManager;
    private final DialogueLoader dialogueLoader;
    private final EffectFactory effectFactory;
    private final ConditionFactory conditionFactory;

    private Personaggio player;
    private String currentDialogueId;
    private List<Nemico> enemyList;

    private static final String SAVE_FILE = "savegame.json"; //hardcoded, da modificare, passare
    //in un altro modo (faccio 3 partite diverse) perché cosi posso solo avere 1 partita con uno stato
   //to do in un secondo momento
    /**
     * Crea il motore di gioco associato a una specifica interfaccia utente.
     * @param ui l'interfaccia utente (console, JavaFX, ...)
     */
    public GameEngine(GameUI ui, List<Nemico> enemyList) {
        this.ui = ui;
        this.saveManager = new JsonSaveManager(SAVE_FILE);
        this.dialogueLoader = new DialogueLoader();
        this.effectFactory = new EffectFactory();
        this.conditionFactory = new ConditionFactory();
        this.enemyList = enemyList;
    }

    /**
     * Avvia una nuova partita con il nome specificato.
     * @param playerName nome del personaggio (se vuoto, usa "Linden")
     */
    public void startNewGame(String playerName) {
        String finalName = (playerName == null || playerName.isBlank()) ? "Linden" : playerName;
        player = new Personaggio(finalName);
        currentDialogueId = "start";

        ui.showMessage("Nuova avventura per " + player.getName());
        ui.onGameStarted();   // per le UI grafiche, qui avverrà il cambio scena

        runCurrentDialogue();
    }

    /**
     * Carica una partita salvata. Se il caricamento fallisce, avvia una nuova partita.
     */
    public void loadGame() {
        try {
            SaveData data = saveManager.load();
            this.player = data.getPlayer();
            this.currentDialogueId = data.getLastDialogueId() != null ? data.getLastDialogueId() : "start";

            ui.showMessage("Partita caricata: " + player.getName());
            ui.onGameStarted();

            runCurrentDialogue();
        } catch (SaveLoadException e) {
            ui.showMessage("Nessun salvataggio valido (" + e.getMessage() + "). Creo nuova partita.");
            startNewGame("Linden");
        }
    }

    /**
     * Esegue il dialogo corrente, applica l'eventuale effetto, avvia la battaglia
     * e infine salva la partita con il checkpoint aggiornato.
     */
    private void runCurrentDialogue() {
        Map<String, Dialogue> dialogues = dialogueLoader.loadFromResource("dialogues.json");
        DialoguePlayer dialoguePlayer = new DialoguePlayer(dialogues, ui, conditionFactory, player);

        dialoguePlayer.play(currentDialogueId).thenCompose(result -> {

            currentDialogueId = result.lastDialogueId();
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
        //non è estendibile ora, è hardcoded, sistemare
        //battaglia di esempio

            Nemico enemy = enemyList.get(new Random().nextInt(0,enemyList.size()));
            BattleManager battleManager = new BattleManager(ui);
            return battleManager.startBattle(player, enemy);
        }).thenRun(() -> {

            try {
                SaveData data = new SaveData(player, currentDialogueId);
                saveManager.save(data);
                ui.showMessage("Partita salvata.");
            } catch (SaveLoadException e) {
                ui.showMessage("Errore salvataggio: " + e.getMessage());
            }
            ui.showMessage("Fine demo.");
        });
    }
}