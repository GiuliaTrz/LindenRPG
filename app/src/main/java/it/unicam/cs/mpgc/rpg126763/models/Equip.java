package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

public enum Equip {
    SWORD("Guerriero", 120, 30, List.of(Skill.FIAMMA, Skill.SALTO_INFUOCATO, Skill.COLPO_DEL_DRAGO)),
    BOW("Arciere", 100, 50, List.of(Skill.COLPO_CRITICO, Skill.FRECCIA_TRIPLA, Skill.OCCHIO_DEL_FALCO)),
    STAFF("Mago", 80, 100, List.of(Skill.SFERA_MAGICA, Skill.CATENE_INCANTATE, Skill.GUARIGIONE));

    private final String className;
    private final int baseHp;
    private final int baseMp;
    private final List<Skill> skills;

    /**
     *
     * @return mostro i dettagli della classe (nome, hp, mp) e tutte le relative skills legate
     * alla classe scelta (utilizzando un foreach)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- class:").append(className).append("\n");
        sb.append("- class Hp:").append(baseHp).append("\n");
        sb.append("- class Mp:").append(baseMp).append("\n");
        sb.append("- abilità:").append("\n");
        for (Skill s : skills){
            sb.append("--").append(s);
        }

        return sb.toString();
    }


    Equip(String className, int hp, int mp, List<Skill> skills) {
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

    public List<Skill> getSkills() {
        return skills;
    }
}
