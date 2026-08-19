package it.unicam.cs.mpgc.rpg126763.dialogue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sostituisce segnaposto del tipo {@code $nome$} nei dialoghi con valori dinamici.
 */
public class ConstantManager {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$(\\w+)\\$");
    private final Map<String, String> constants = new HashMap<>();

    /**
     * Registra una costante.
     * @return questa istanza per permettere chiamate a catena
     */
    public ConstantManager setConstant(String name, String value) {
        constants.put(name, value);
        return this;
    }

    /**
     * Applica la sostituzione a tutti i dialoghi (testo, speaker e opzioni).
     */
    public void apply(Map<String, Dialogue> dialogues) {
        dialogues.values().forEach(this::replaceInDialogue);
    }

    private void replaceInDialogue(Dialogue dialogue) {
        dialogue.setText(replace(dialogue.getText()));

        if (dialogue.getOptions() != null) {
            dialogue.getOptions().forEach(option ->
                    option.setText(replace(option.getText()))
            );
        }
    }

    /**
     * Sostituisce i segnaposto nel testo.
     */
    private String replace(String text) {
        if (text == null) return null;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);          // nome della costante (senza $)
            String replacement = constants.getOrDefault(key, matcher.group(0));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();

    }
}