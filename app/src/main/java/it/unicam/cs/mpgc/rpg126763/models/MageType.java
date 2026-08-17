package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

/**
 * Enum dei tipi di mago elementale (fuoco, acqua, vento, terra). Contiene le statistiche di base
 * e le skill associate, oltre al flavour text per l'effetto narrativo.
 */
public enum MageType {

    EMBERWEAVER("Emberweaver", 80, 100,
            List.of(Skill.EMBER_SPEAR, Skill.FLAME_STEP, Skill.ASHEN_BURST),
            "Una fiamma si accende dentro di te..."),

    TIDEWEAVER("Tideweaver", 90, 110,
            List.of(Skill.WATER_LULL, Skill.TIDE_BIND, Skill.RESTORE_FLOW),
            "Senti il fluire dell'acqua..."),

    GUSTWEAVER("Gustweaver", 100, 90,
            List.of(Skill.WIND_CUT, Skill.FEATHER_STEP, Skill.SKY_PIERCE),
            "Il vento risponde al tuo respiro..."),

    EARTHWEAVER("Earthweaver", 120, 70,
            List.of(Skill.STONE_SKIN, Skill.ROOT_GRASP, Skill.GEODE_BREAK),
            "Le radici ti ancorano alla terra...");

    private final String name;
    private final int baseHp;
    private final int baseMp;
    private final List<Skill> skills;
    private final String flavorText;   // <-- campo aggiunto

    MageType(String name, int baseHp, int baseMp, List<Skill> skills, String flavorText) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.skills = skills;
        this.flavorText = flavorText;
    }

    public String getName() { return name; }
    public int getBaseHp() { return baseHp; }
    public int getBaseMp() { return baseMp; }
    public List<Skill> getSkills() { return skills; }

    /**
     * Restituisce il testo descrittivo associato al tipo di mago
     * @return flavour text
     */
    public String getFlavorText() { return flavorText; }

    @Override
    public String toString() { return name; }
}