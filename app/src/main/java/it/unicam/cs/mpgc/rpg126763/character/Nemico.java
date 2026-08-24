package it.unicam.cs.mpgc.rpg126763.character;

/**
 * E' un nemico generico; estende {@link CombatCharacter}
 * aggiungendo un attacco base fisso
 */
public class Nemico extends CombatCharacter {
    private int attack;

    /** Costruttore vuoto per Gson */
    public Nemico() {}

    /**
     * Crea un nemico con i parametri indicati
     * @param name   nome
     * @param hp     punti vita (nessun MP)
     * @param attack danno inflitto per attacco
     */
    public Nemico(String name, int hp, int attack, String imagePath) {
        super(name, hp, 0, imagePath);
        this.attack = attack;
    }

    /**
     * Crea un nemico con i parametri indicati
     * @param name   nome
     * @param hp     punti vita (nessun MP)
     * @param attack danno inflitto per attacco
     */
    public Nemico(String name, int hp, int attack) {
        super(name, hp, 0);
        this.attack = attack;
    }

    /** @return il valore dell'attacco base */
    public int getAttack() { return attack; }

    @Override
    public String toString() {
        return String.format("%s [HP:%d, ATK:%d]", name, hp, attack);
    }
}