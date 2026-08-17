package it.unicam.cs.mpgc.rpg126763.character;

/**
 * Personaggio non giocante (NPC) usato nei dialoghi
 * non partecipa ai combattimenti, per questo motivo non estende CombatCharacter
 */
public class NPC {
    private String name;
    private String dialogueHint;   //suggerimento opzionale per il dialogo

    public NPC() {}
    public NPC(String name) { this.name = name; }

    public String getName() { return name; }
    public String getDialogueHint() { return dialogueHint; }

    @Override
    public String toString() { return name; }
}