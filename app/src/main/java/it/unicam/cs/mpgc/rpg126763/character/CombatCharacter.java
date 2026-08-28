package it.unicam.cs.mpgc.rpg126763.character;

/**
 * Classe base astratta per i personaggi che partecipano ai combattimenti.
 * Definisce attributi comuni come nome, punti vita (HP), punti magia (MP),
 * valori massimi e percorso dell'immagine rappresentativa.
 *
 * <p>Fornisce metodi per gestire danni, guarigioni e consumo di MP,
 * oltre a metodi di utilità per verificare lo stato vitale e ottenere
 * una rappresentazione testuale leggibile.</p>
 *
 * <p>Le sottoclassi concrete possono estendere questa base per aggiungere
 * comportamenti specifici.</p>
 */
public abstract class CombatCharacter {
    protected String name;
    protected int hp;
    protected int mp;
    protected int maxHp;
    protected int maxMp;
    protected String imagePath;

    /**
     * Costruttore vuoto protetto, utile per la deserializzazione o per sottoclassi
     * che necessitano di inizializzazione successiva.
     */
    protected CombatCharacter() {}

    /**
     * Costruttore protetto che imposta nome, HP, MP e immagine.
     * I valori massimi di HP e MP vengono inizializzati ai valori attuali passati.
     *
     * @param name      il nome del personaggio
     * @param hp        i punti vita iniziali (e massimi)
     * @param mp        i punti magia iniziali (e massimi)
     * @param imagePath il percorso dell'immagine del personaggio
     */
    protected CombatCharacter(String name, int hp, int mp, String imagePath) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.maxHp = hp;
        this.maxMp = mp;
        this.imagePath = imagePath;
    }

    /**
     * Costruttore protetto che imposta nome, HP e MP senza immagine.
     * I valori massimi di HP e MP vengono inizializzati ai valori attuali passati.
     *
     * @param name il nome del personaggio
     * @param hp   i punti vita iniziali (e massimi)
     * @param mp   i punti magia iniziali (e massimi)
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
     * Verifica se il personaggio è ancora in vita.
     *
     * @return {@code true} se gli HP sono maggiori di 0, altrimenti {@code false}
     */
    public boolean isAlive() { return hp > 0; }

    /**
     * Applica un danno al personaggio, riducendo gli HP.
     * Il danno viene ignorato se non positivo e gli HP non scendono sotto zero.
     *
     * @param dmg il danno da infliggere
     */
    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp = Math.max(0, hp - dmg);
        }
    }

    /**
     * Cura il personaggio, aumentando gli HP fino al massimo consentito.
     * La cura viene ignorata se non positiva.
     *
     * @param amount la quantità di HP da recuperare
     */
    public void heal(int amount) {
        if (amount > 0) {
            hp = Math.min(maxHp, hp + amount);
        }
    }

    /**
     * Consuma una quantità di MP, riducendoli senza scendere sotto zero.
     * Il consumo viene ignorato se non positivo.
     *
     * @param amount la quantità di MP da consumare
     */
    public void consumeMp(int amount) {
        if (amount > 0) {
            mp = Math.max(0, mp - amount);
        }
    }

    /**
     * Restituisce una rappresentazione testuale del personaggio con nome e statistiche correnti.
     *
     * @return una stringa nel formato "nome [HP:x/y MP:a/b]"
     */
    @Override
    public String toString() {
        return String.format("%s [HP:%d/%d MP:%d/%d]", name, hp, maxHp, mp, maxMp);
    }

    public String getImagePath() {
        return imagePath;
    }
}