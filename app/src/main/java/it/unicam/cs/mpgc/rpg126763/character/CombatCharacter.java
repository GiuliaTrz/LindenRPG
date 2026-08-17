package it.unicam.cs.mpgc.rpg126763.character;

/**
 * Classe base astratta per ogni personaggio che partecipa a combattimenti.
 * Contiene gli attributi comuni (HP, MP, massimali) e i metodi per
 * applicare danni, cure e consumare MP.
 */
public abstract class CombatCharacter {
    protected String name;
    /** Punti vita correnti */
    protected int hp;
    /** Punti magia correnti */
    protected int mp;
    protected int maxHp;
    protected int maxMp;

    /** Costruttore vuoto necessario per la serializzazione JSON */
    protected CombatCharacter() {}

    /**
     * Crea un nuovo personaggio combattente con i valori specificati.
     * @param name nome
     * @param hp   punti vita iniziali (e massimi)
     * @param mp   punti magia iniziali (e massimi)
     */
    protected CombatCharacter(String name, int hp, int mp) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.maxHp = hp;
        this.maxMp = mp;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMp() { return maxMp; }

    /**
     * Verifica se il personaggio è ancora vivo (HP > 0).
     * @return true se vivo
     */
    public boolean isAlive() { return hp > 0; }

    /**
     * Sottrae una quantità di danni, evitando che gli HP diventino negativi.
     * @param dmg quantità di danno (se ≤ 0 non fa nulla)
     */
    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp = Math.max(0, hp - dmg);
        }
    }

    /**
     * Ripristina una quantità di HP, senza superare il massimo.
     * @param amount punti da recuperare
     */
    public void heal(int amount) {
        if (amount > 0) {
            hp = Math.min(maxHp, hp + amount);
        }
    }

    /**
     * Consuma MP per un'abilità, senza andare sotto zero.
     * @param amount MP da consumare
     */
    public void consumeMp(int amount) {
        if (amount > 0) {
            mp = Math.max(0, mp - amount);
        }
    }

    /**
     * Ricalcola i massimi di HP e MP basandosi sui valori correnti.
     * Da chiamare dopo il caricamento da JSON per ripristinare i massimali.
     */
    public void recalculateMaxStats() {
        this.maxHp = this.hp;
        this.maxMp = this.mp;
    }

    @Override
    public String toString() {
        return String.format("%s [HP:%d/%d MP:%d/%d]", name, hp, maxHp, mp, maxMp);
    }
}