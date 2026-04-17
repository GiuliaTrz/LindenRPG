package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

public enum MageType {

    EMBERWEAVER("Emberweaver", 80, 100,
            List.of(Skill.EMBER_SPEAR, Skill.FLAME_STEP, Skill.ASHEN_BURST)),

    TIDEWEAVER("Tideweaver", 90, 110,
            List.of(Skill.WATER_LULL, Skill.TIDE_BIND, Skill.RESTORE_FLOW)),

    GUSTWEAVER("Gustweaver", 100, 90,
            List.of(Skill.WIND_CUT, Skill.FEATHER_STEP, Skill.SKY_PIERCE)),

    EARTHWEAVER("Earthweaver", 120, 70,
            List.of(Skill.STONE_SKIN, Skill.ROOT_GRASP, Skill.GEODE_BREAK));

    private final String name;
    private final int baseHp;
    private final int baseMp;
    private final List<Skill> skills;

    MageType(String name, int baseHp, int baseMp, List<Skill> skills) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseMp() {
        return baseMp;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return name;
    }
}