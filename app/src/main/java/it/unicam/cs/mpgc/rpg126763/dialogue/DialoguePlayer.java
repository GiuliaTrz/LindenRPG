package it.unicam.cs.mpgc.rpg126763.dialogue;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * questa classe mostra i dialoghi e raccoglie le scelte usandola GameUI,
 * filtra le opzioni in base alle condizioni e
 * restituisce l'ultimo effetto selezionato (oppure null)
 */
public class DialoguePlayer {
    private final Map<String, Dialogue> dialogues;
    private final GameUI ui;
    private final ConditionFactory conditionFactory;
    private final Personaggio player;
    private ConstantManager constantManager;

    public DialoguePlayer(Map<String, Dialogue> dialogues, GameUI ui,
                          ConditionFactory conditionFactory, Personaggio player) {
        this.dialogues = dialogues;
        this.ui = ui;
        this.conditionFactory = conditionFactory;
        this.player = player;
        this.constantManager = new ConstantManager();
        setConstant();
        constantManager.apply(dialogues);
    }

    private void setConstant(){
        this.constantManager.setConstant("playerName", player.getName());
    }

    /**
     * Esegue il dialogo a partire da un ID
     * @param startId ID del nodo iniziale
     * @return CompletableFuture che completa con un DialogueResult
     */
    public CompletableFuture<DialogueResult> play(String startId) {
        CompletableFuture<DialogueResult> future = new CompletableFuture<>();
        processNode(startId, null, future);
        return future;
    }

    private void processNode(String nodeId, String lastEffect, CompletableFuture<DialogueResult> future) {
        Dialogue current = dialogues.get(nodeId);
        if (current == null) {
            future.complete(new DialogueResult(lastEffect, nodeId));
            return;
        }

        String speaker = current.getSpeaker();
        String text = (speaker != null && !speaker.isBlank())
                ? speaker + ": " + current.getText()
                : current.getText();
        ui.showMessage(text);

        if (!current.hasOptions()) {
            future.complete(new DialogueResult(lastEffect, current.getId()));
            return;
        }

        List<Option> validOptions = current.getOptions().stream()
                .filter(opt -> {
                    if (opt.getCondition() == null) return true;
                    DialogueCondition cond = conditionFactory.get(opt.getCondition());
                    return cond != null && cond.check(player);
                })
                .collect(Collectors.toList());

        if (validOptions.isEmpty()) {
            future.complete(new DialogueResult(lastEffect, current.getId()));
            return;
        }

        List<String> optionTexts = validOptions.stream()
                .map(Option::getText)
                .collect(Collectors.toList());

        ui.choose("Cosa fai?", optionTexts).thenAccept(index -> {
            Option selected = validOptions.get(index);
            String newEffect = selected.getEffect() != null ? selected.getEffect() : lastEffect;
            processNode(selected.getNextDialogueId(), newEffect, future);
        });
    }
}