package it.unicam.cs.mpgc.rpg126763.dialogue;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.ConditionFactory;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.DialogueCondition;
import it.unicam.cs.mpgc.rpg126763.persistence.GameState;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * La classe {@code DialoguePlayer} si occupa dell'esecuzione dei dialoghi interattivi.
 * Gestisce il flusso di un dialogo a partire da un nodo iniziale, mostrando i messaggi
 * all'utente e raccogliendo le sue scelte tramite un'interfaccia {@link GameUI}.
 * Le scelte possono essere condizionate da regole definite tramite {@link DialogueCondition}
 * e possono attivare effetti (es. cambiare lo stato del gioco).
 *
 *
 * Il dialogo è rappresentato da un grafo di nodi (istanze di {@link Dialogue}),
 * ciascuno con un {@code id} univoco, un testo, uno speaker opzionale e una lista di {@link Option}.
 * Il giocatore può scegliere un'opzione e il gioco prosegue sul nodo successivo indicato da {@code nextDialogueId}.
 *
 *
 * Il flusso è asincrono e restituisce un {@link CompletableFuture<DialogueResult>} che
 * si completa quando il dialogo termina (o quando si raggiunge un nodo finale).
 *
 *
 * @see Dialogue
 * @see Option
 * @see GameUI
 * @see ConditionFactory
 * @see ConstantManager
 * @see DialogueResult
 */
public class DialoguePlayer {

    /** Mappa degli ID dei dialoghi ai rispettivi oggetti {@link Dialogue}. */
    private final Map<String, Dialogue> dialogues;

    /** Interfaccia utente per mostrare messaggi e ricevere input. */
    private final GameUI ui;

    /** Factory per creare condizioni di validità delle opzioni a partire da una chiave. */
    private final ConditionFactory conditionFactory;

    /** Gestore delle costanti per la sostituzione dei placeholder (es. {@code $playerName$}). */
    private final ConstantManager constantManager;

    /** Stato corrente del gioco (contiene il personaggio e altre informazioni di contesto). */
    private GameState gameState;

    /**
     * Costruisce un {@code DialoguePlayer} con le dipendenze necessarie.
     *
     * @param dialogues        mappa dei dialoghi (ID → {@link Dialogue}).
     * @param ui               l'interfaccia utente per interagire con il giocatore.
     * @param conditionFactory factory per ottenere le condizioni di validità delle opzioni.
     * @param gameState        lo stato corrente del gioco (contiene il personaggio e i dati di partita).
     */
    public DialoguePlayer(Map<String, Dialogue> dialogues, GameUI ui,
                          ConditionFactory conditionFactory, GameState gameState) {
        this.gameState = gameState;
        this.dialogues = dialogues;
        this.ui = ui;
        this.conditionFactory = conditionFactory;
        this.constantManager = new ConstantManager();
        setConstants();          // registra le costanti per la sostituzione placeholder
        constantManager.apply(dialogues);
    }

    /**
     * Registra le costanti utilizzate nei placeholder all'interno dei testi dei dialoghi.
     *
     * Ad esempio, sostituisce {@code $playerName$} con il nome effettivo del personaggio
     * recuperato da {@link GameState#getPlayer()}.
     *
     */
    private void setConstants() {
        constantManager.setConstant("playerName", gameState.getPlayer().getName());
    }

    /**
     * Avvia l'esecuzione del dialogo a partire da un nodo iniziale.
     *
     * @param startId l'ID del nodo di dialogo da cui iniziare.
     * @return un {@link CompletableFuture} che fornisce il {@link DialogueResult} al termine del dialogo.
     */
    public CompletableFuture<DialogueResult> play(String startId) {
        return processNode(startId, null);
    }

    /**
     * Elabora ricorsivamente un nodo di dialogo.
     *
     * Questo metodo:
     *
     *-Aggiorna l'ID del dialogo corrente nello stato di gioco.
     *-Recupera il nodo corrispondente all'ID.
     *-Mostra il testo del dialogo e lo speaker (se presente).
     *-Se il nodo ha opzioni, le filtra in base alle condizioni e le mostra all'utente.
     *-Raccoglie la scelta e prosegue sul nodo successivo.
     *-Se non ci sono opzioni o il nodo non esiste, completa il risultato.
     *
     * @param nodeId     l'ID del nodo di dialogo corrente.
     * @param lastEffect l'effetto accumulato dalle scelte precedenti (può essere {@code null}).
     * @return un {@link CompletableFuture} che si completa con il {@link DialogueResult} finale.
     */
    private CompletableFuture<DialogueResult> processNode(String nodeId, String lastEffect) {
        gameState.setCurrentDialogueId(nodeId);

        // pulisce l'ID da eventuali spazi bianchi
        nodeId = nodeId.trim();

        Dialogue current = dialogues.get(nodeId);
        if (current == null) {
            // nodo non trovato: completa con un risultato di fallback (fine dialogo)
            return CompletableFuture.completedFuture(new DialogueResult(lastEffect, "end", false));
        }

        String speaker = current.getSpeaker();
        ui.setSpeakerImage(speaker);   // aggiorna eventuale immagine dello speaker nell'UI

        String text = (current.getSpeakerDialogue() != null && !current.getSpeakerDialogue().isBlank())
                ? current.getSpeakerDialogue() + ": " + current.getText()
                : current.getText();
        ui.showMessage(text);

        if (!current.hasOptions()) {
            return CompletableFuture.completedFuture(
                    new DialogueResult(lastEffect, current.getId(), current.isBattle())
            );
        }

        List<Option> validOptions = current.getOptions().stream()
                .filter(opt -> {
                    if (opt.getCondition() == null) return true;
                    DialogueCondition cond = conditionFactory.get(opt.getCondition());
                    return cond != null && cond.check(gameState.getPlayer());
                })
                .collect(Collectors.toList());

        if (validOptions.isEmpty()) {
            return CompletableFuture.completedFuture(
                    new DialogueResult(lastEffect, current.getId(), current.isBattle())
            );
        }

        List<String> optionTexts = validOptions.stream()
                .map(Option::getText)
                .collect(Collectors.toList());
        return ui.choose("Cosa vuoi fare?", optionTexts)
                .thenCompose(index -> {
                    Option selected = validOptions.get(index);
                    String newEffect = (selected.getEffect() != null) ? selected.getEffect() : lastEffect;
                    String nextId = selected.getNextDialogueId();
                    return processNode(nextId, newEffect);
                });
    }
}