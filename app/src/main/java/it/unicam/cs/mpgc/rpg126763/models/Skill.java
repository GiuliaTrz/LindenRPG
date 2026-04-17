package it.unicam.cs.mpgc.rpg126763.models;

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

    Skill(String name, int heal, int costMp, int damage, int enemyMpDamage) {
        this.name = name;
        this.heal = heal;
        this.costMp = costMp;
        this.damage = damage;
        this.enemyMpDamage = enemyMpDamage;
    }

    public String getName() {
        return name;
    }

    public int getHeal() {
        return heal;
    }

    public int getCostMp() {
        return costMp;
    }

    public int getDamage() {
        return damage;
    }

    public int getEnemyMpDamage() {
        return enemyMpDamage;
    }

    @Override
    public String toString() {
        return name;
    }
}
