package it.unicam.cs.mpgc.rpg126763.character;

/**
 * Personaggio non giocante (NPC) usato nei dialoghi
 * non partecipa ai combattimenti, per questo motivo non estende CombatCharacter
 */
public class NPC {
    private String name;
    public NPC() {}
    public NPC(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public String toString() { return name; }
}