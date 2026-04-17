package it.unicam.cs.mpgc.rpg126763.models;

public class NPC {

    private String name;
    private String dialogueHint; // piccola estensione utile

    public NPC() {}

    public NPC(String name) {
        this.name = name;
    }

    public NPC(String name, String dialogueHint) {
        this.name = name;
        this.dialogueHint = dialogueHint;
    }

    public String getName() {
        return name;
    }

    public String getDialogueHint() {
        return dialogueHint;
    }

    @Override
    public String toString() {
        return name;
    }
}