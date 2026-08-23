package it.unicam.cs.mpgc.rpg126763.dialogue;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.ConditionFactory;
import it.unicam.cs.mpgc.rpg126763.dialogue.conditions.DialogueCondition;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DialoguePlayer {

    private final Map<String, Dialogue> dialogues;
    private final GameUI ui;
    private final ConditionFactory conditionFactory;
    private final Personaggio player;
    private final ConstantManager constantManager;

    public DialoguePlayer(Map<String, Dialogue> dialogues, GameUI ui,
                          ConditionFactory conditionFactory, Personaggio player) {
        this.dialogues = dialogues;
        this.ui = ui;
        this.conditionFactory = conditionFactory;
        this.player = player;
        this.constantManager = new ConstantManager();
        setConstants();
        constantManager.apply(dialogues);
    }

    private void setConstants() {

        constantManager.setConstant("playerName", player.getName());
    }

    public CompletableFuture<DialogueResult> play(String startId) {
        return processNode(startId, null);
    }

    private CompletableFuture<DialogueResult> processNode(String nodeId, String lastEffect) {
        this.player.setDialogueId(nodeId);
        nodeId = nodeId.trim();
        Dialogue current = dialogues.get(nodeId);
        if (current == null) {
            return CompletableFuture.completedFuture(new DialogueResult(lastEffect, "end", false));
        }

        String speaker = current.getSpeaker();
        String text = (speaker != null && !speaker.isBlank())
                ? speaker + ": " + current.getText()
                : current.getText();
        ui.showMessage(text);

        if (!current.hasOptions()) {
            return CompletableFuture.completedFuture(new DialogueResult(lastEffect, current.getId(), current.isBattle()));
        }

        List<Option> validOptions = current.getOptions().stream()
                .filter(opt -> {
                    if (opt.getCondition() == null) return true;
                    DialogueCondition cond = conditionFactory.get(opt.getCondition());
                    return cond != null && cond.check(player);
                })
                .collect(Collectors.toList());

        if (validOptions.isEmpty()) {
            return CompletableFuture.completedFuture(new DialogueResult(lastEffect, current.getId(), current.isBattle()));
        }

        List<String> optionTexts = validOptions.stream()
                .map(Option::getText)
                .collect(Collectors.toList());

        return ui.choose("Cosa vuoi fare?", optionTexts)
                .thenCompose(index -> {
                    Option selected = validOptions.get(index);
                    String newEffect = selected.getEffect() != null ? selected.getEffect() : lastEffect;
                    String nextId = selected.getNextDialogueId();
                    return processNode(nextId, newEffect);
                });
    }
}