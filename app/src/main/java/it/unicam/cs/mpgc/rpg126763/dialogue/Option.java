package it.unicam.cs.mpgc.rpg126763.dialogue;

/**
 * Una scelta in un dialogo. Può avere condizione ed effetto.
 */
public class Option {
    private String id;
    private String text;
    private String nextDialogueId;
    private String effect;        // chiave per EffectFactory
    private String condition;     // chiave per ConditionFactory (opzionale)

    public String getId() { return id; }
    public String getText() { return text; }
    public String getNextDialogueId() { return nextDialogueId; }
    public String getEffect() { return effect; }
    public String getCondition() { return condition; }
}