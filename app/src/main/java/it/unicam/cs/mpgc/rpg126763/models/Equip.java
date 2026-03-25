package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

public enum Equip {
    SWORD("Guerriero", 120, 30, List.of("Fiamma", "Colpo del drago", "Doppio scudo")),
    BOW("Ranger", 100, 50, List.of("Freccia tripla", "Occhio del falco", "Colpo critico")),
    STAFF("Mago", 80, 100, List.of("Catene incantate", "Guarigione", "Scudo magico"));

    private final String className;
    private final int baseHp;
    private final int baseMp;
    private final List<String> skills;

    Equip(String className, int hp, int mp, List<String> skills) {
        this.className = className;
        this.baseHp = hp;
        this.baseMp = mp;
        this.skills = skills;
    }

    public String getClassName() {
        return className;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseMp() {
        return baseMp;
    }

    public List<String> getSkills() {
        return skills;
    }
}
