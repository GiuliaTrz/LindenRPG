package it.unicam.cs.mpgc.rpg126763.models;

/**
 * Rappresenta le abilità utilizzabili dai personaggi durante i combattimenti.
 * Ogni skill ha un nome, un costo in MP, un eventuale danno inflitto al nemico,
 * una eventuale cura per il giocatore e un eventuale danno agli MP del nemico.
 *
 * <p>Le costanti sono raggruppate per tipo di mago elementale:
 * Emberweaver (fuoco), Tideweaver (acqua), Gustweaver (vento) e Earthweaver (terra).</p>
 */
public enum Skill {

    // Emberweaver
    EMBER_SPEAR("Ember Spear", 0, 5, 15, 0),
    FLAME_STEP("Flame Step", 0, 8, 10, 0),
    ASHEN_BURST("Ashen Burst", 0, 12, 25, 0),

    // Tideweaver
    WATER_LULL("Water Lull", 10, 6, 0, 0),
    TIDE_BIND("Tide Bind", 0, 10, 15, 0),
    RESTORE_FLOW("Restore Flow", 25, 12, 0, 0),

    // Gustweaver
    WIND_CUT("Wind Cut", 0, 6, 18, 0),
    FEATHER_STEP("Feather Step", 5, 5, 0, 0),
    SKY_PIERCE("Sky Pierce", 0, 10, 22, 0),

    // Earthweaver
    STONE_SKIN("Stone Skin", 15, 8, 0, 0),
    ROOT_GRASP("Root Grasp", 0, 10, 20, 0),
    GEODE_BREAK("Geode Break", 0, 15, 30, 0);

    private final String name;
    private final int heal;
    private final int costMp;
    private final int damage;
    private final int enemyMpDamage;

    /**
     * Costruttore delle costanti enum.
     *
     * @param name          nome leggibile della skill
     * @param heal          quantità di HP recuperati dal giocatore (0 se non cura)
     * @param costMp        costo in MP per utilizzare la skill
     * @param damage        danno inflitto al nemico (0 se non danneggia)
     * @param enemyMpDamage danno agli MP del nemico (attualmente non utilizzato, ma inserito per future versioni)
     */
    Skill(String name, int heal, int costMp, int damage, int enemyMpDamage) {
        this.name = name;
        this.heal = heal;
        this.costMp = costMp;
        this.damage = damage;
        this.enemyMpDamage = enemyMpDamage;
    }

    /**
     * Restituisce il nome leggibile della skill.
     *
     * @return il nome della skill
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la quantità di HP che la skill permette di recuperare.
     *
     * @return la cura fornita (0 se non cura)
     */
    public int getHeal() {
        return heal;
    }

    /**
     * Restituisce il costo in MP per utilizzare la skill.
     *
     * @return il costo in MP
     */
    public int getCostMp() {
        return costMp;
    }

    /**
     * Restituisce il danno inflitto al nemico dalla skill.
     *
     * @return il danno (0 se non danneggia)
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Restituisce il danno agli MP del nemico.
     * Attualmente non è utilizzato nella logica di combattimento,
     * ma è previsto per future estensioni.
     *
     * @return il danno agli MP del nemico
     */
    public int getEnemyMpDamage() {
        return enemyMpDamage;
    }

    /**
     * Restituisce il nome leggibile della skill.
     *
     * @return il nome della skill
     */
    @Override
    public String toString() {
        return name;
    }
}