package it.unicam.cs.mpgc.rpg126763.engine;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.Dialogue;
import it.unicam.cs.mpgc.rpg126763.dialogue.DialogueLoader;
import it.unicam.cs.mpgc.rpg126763.dialogue.DialoguePlayer;
import it.unicam.cs.mpgc.rpg126763.dialogue.DialogueResult;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.ConditionFactory;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.DialogueEffect;
import it.unicam.cs.mpgc.rpg126763.dialogue.effects.EffectFactory;
import it.unicam.cs.mpgc.rpg126763.persistence.GameState;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveLoadException;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveManager;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


/**
 * Orchestratore del flusso di gioco.
 * Coordina il caricamento dei dialoghi, la gestione degli effetti,
 * l'avvio delle battaglie e la persistenza dello stato.
 */
public class GameEngine {

    private static final String DEFAULT_PLAYER_NAME = "Linden";
    private static final String START_DIALOGUE_ID = "start";
    private static final String END_DIALOGUE_ID = "end";
    private static final String DIALOGUES_RESOURCE = "dialogues.json";

    private final GameUI ui;
    private final SaveManager saveManager;
    private final EffectFactory effectFactory;
    private final ConditionFactory conditionFactory;
    private final List<Nemico> enemyList;
    private final Map<String, Dialogue> dialogues;
    private final BattleManager battleManager;

    private GameState gameState;

    /**
     * Costruttore principale che crea le dipendenze di default.(lo usa main window controller)
     */
    public GameEngine(GameUI ui, List<Nemico> enemyList, SaveManager saveManager, BattleManager battleManager) {
        this(ui, enemyList,
                new DialogueLoader(),
                new EffectFactory(),
                new ConditionFactory(),
                saveManager,
                battleManager);
    }

    /**
     * Costruttore che permette l'iniezione delle dipendenze (Dependency Injection)
     * per facilitare i test e il disaccoppiamento.
     */
    public GameEngine(GameUI ui, List<Nemico> enemyList,
                      DialogueLoader dialogueLoader,
                      EffectFactory effectFactory,
                      ConditionFactory conditionFactory,
                      SaveManager saveManager,
                      BattleManager battleManager) {
        this.ui = ui;
        this.enemyList = enemyList;
        this.effectFactory = effectFactory;
        this.conditionFactory = conditionFactory;
        this.saveManager = saveManager;
        this.gameState = new GameState();
        this.dialogues = dialogueLoader.loadFromResource(DIALOGUES_RESOURCE);
        this.battleManager = battleManager;
        if (enemyList.isEmpty()) {
            throw new IllegalStateException("La lista dei nemici non può essere vuota.");
        }
    }

    /**
     * Avvia una nuova partita creando un personaggio e inizializzando il dialogo iniziale.
     *
     * @param playerName Il nome del personaggio scelto dal giocatore.
     */
    public void startNewGame(String playerName) {
        String finalName = (playerName == null || playerName.isBlank()) ? DEFAULT_PLAYER_NAME : playerName;
        gameState.setPlayer(new Personaggio(finalName));
        gameState.setCurrentDialogueId(START_DIALOGUE_ID);

        ui.showMessage("Nuova avventura per " + gameState.getPlayer().getName());
        ui.onGameStarted();
        runCurrentDialogue();
    }

    /**
     * Carica una partita esistente dal file di salvataggio.
     * Se il salvataggio non è valido, avvia automaticamente una nuova partita.
     */
    public void loadGame() {
        try {
            this.gameState = saveManager.load();
            ui.showMessage("Partita caricata: " + gameState.getPlayer().getName());
            ui.onGameStarted();

            runCurrentDialogue();
        } catch (SaveLoadException e) {
            ui.showMessage("Nessun salvataggio valido (" + e.getMessage() + "). Creo nuova partita.");
            startNewGame(DEFAULT_PLAYER_NAME);
        }
    }

    /**
     * Salva lo stato corrente della partita su disco.
     */
    public void saveGame() {
        try {
            saveManager.save(gameState);
            ui.showMessage("Partita salvata.");
        } catch (SaveLoadException e) {
            ui.showMessage("Errore salvataggio: " + e.getMessage());
        }
    }

    /**
     * Esegue il dialogo corrente e gestisce la catena di eventi asincroni
     * (effetti, battaglie e salvataggio automatico).
     */
    private void runCurrentDialogue() {
        DialoguePlayer dialoguePlayer = new DialoguePlayer(dialogues, ui, conditionFactory, gameState);

        dialoguePlayer.play(gameState.getCurrentDialogueId())
                .thenCompose(result -> {
                    updateGameStateAfterDialogue(result);

                    if (result.battle()) {
                        return startBattleIfNeeded();
                    }
                    return CompletableFuture.completedFuture(null);
                })
                .thenRun(this::handleEndOfTurnSave);
    }

    /**
     * Aggiorna lo stato del gioco dopo la conclusione di un dialogo
     * (nuovo ID e applicazione degli eventuali effetti).
     *
     * @param result Il risultato del dialogo appena concluso.
     */
    private void updateGameStateAfterDialogue(DialogueResult result) {
        gameState.setCurrentDialogueId(result.lastDialogueId());
        processEffect(result);
    }

    /**
     * Applica l'effetto (se presente) al personaggio e mostra la descrizione.
     *
     * @param result Il risultato del dialogo contenente l'eventuale chiave effetto.
     */
    private void processEffect(DialogueResult result) {
        if (result.effectKey() == null) {
            return;
        }

        DialogueEffect effect = effectFactory.get(result.effectKey());
        if (effect != null) {
            effect.apply(gameState.getPlayer());
            String description = effect.getDescription();
            if (description != null) {
                ui.showMessage(description);
            }
        }
    }

    /**
     * Avvia una battaglia scegliendo un nemico casuale se non già impostato.
     *
     * @return Un future che si completa quando la battaglia termina.
     */
    private CompletableFuture<Void> startBattleIfNeeded() {
        if (gameState.getEnemy() == null) {
            gameState.setEnemy(enemyList.get(new Random().nextInt(enemyList.size())));
        }
        ui.setEnemyImage(gameState.getEnemy().getImagePath());
        ui.showMessage("Un " + gameState.getEnemy().getName() + " appare! Preparati al combattimento!");

        return battleManager.startBattle(gameState);
    }

    /**
     * Gestisce il salvataggio automatico alla fine di ogni turno di gioco
     * (a meno che il gioco non sia terminato).
     */
    private void handleEndOfTurnSave() {
        if (!END_DIALOGUE_ID.equals(gameState.getCurrentDialogueId())) {
            try {
                saveManager.save(gameState);
                ui.showMessage("Partita salvata.");
            } catch (SaveLoadException e) {
                ui.showMessage("Errore salvataggio: " + e.getMessage());
            }
        } else {
            ui.showMessage("La demo è finita. Grazie per aver giocato!");
        }
    }
}