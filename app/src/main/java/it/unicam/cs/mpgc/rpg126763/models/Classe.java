package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

public enum Classe {

    FUOCO("Mago del Fuoco", 80, 100,
            List.of(Skill.FIAMMA, Skill.SALTO_INFUOCATO, Skill.COLPO_DEL_DRAGO)),

    ACQUA("Mago dell'Acqua", 90, 110,
            List.of(Skill.GUARIGIONE, Skill.CATENE_INCANTATE, Skill.SFERA_MAGICA)),

    ARIA("Mago dell'Aria", 100, 80,
            List.of(Skill.FRECCIA_TRIPLA, Skill.OCCHIO_DEL_FALCO, Skill.COLPO_CRITICO)),

    TERRA("Mago della Terra", 120, 60,
            List.of(Skill.COLPO_DEL_DRAGO, Skill.CATENE_INCANTATE, Skill.GUARIGIONE));

    private final String nome;
    private final int baseHp;
    private final int baseMp;
    private final List<Skill> skills;

    Classe(String nome, int hp, int mp, List<Skill> skills) {
        this.nome = nome;
        this.baseHp = hp;
        this.baseMp = mp;
        this.skills = skills;
    }

    public String getNome() {
        return nome;
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
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append("\n");
        sb.append("HP: ").append(baseHp).append("\n");
        sb.append("MP: ").append(baseMp).append("\n");
        sb.append("Abilità:\n");

        for (Skill s : skills) {
            sb.append("- ").append(s).append("\n");
        }

        return sb.toString();
    }
}