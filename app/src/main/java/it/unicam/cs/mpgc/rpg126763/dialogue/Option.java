package it.unicam.cs.mpgc.rpg126763.dialogue;

public class Option {

    private String id;
    private String text;
    private String nextDialogueId;
    private String effect;

    public Option() {}

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getNextDialogueId() {
        return nextDialogueId;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return text;
    }
}