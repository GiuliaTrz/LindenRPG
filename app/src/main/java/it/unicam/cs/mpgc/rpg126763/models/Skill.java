package it.unicam.cs.mpgc.rpg126763.models;

import java.util.List;

public enum Skill {
    FIAMMA ("Fiamma", 0, -3, -10, 0),
    COLPO_DEL_DRAGO( "Colpo del Drago", 0, -5, -15, 0),
    SALTO_INFUOCATO ("Salto infuocato", 0, -8, -20, 0  ),
    FRECCIA_TRIPLA ("Freccia tripla", 0, -5, -20, 0),
    OCCHIO_DEL_FALCO ("Occhio del falco", +5, -5, 0, 0),
    COLPO_CRITICO ("Colpo critico", 0, -10, -25, 0),
    CATENE_INCANTATE ("Catene incantate", 0, -20, -10, 0),
    GUARIGIONE("Guarigione", +20, -10, 0, 0),
    SFERA_MAGICA ("Sfera magica", 0, -15, -5, 0),
    ;

    private final int hpPersonaggio;
    private final int mpPersonaggio;
    private final int hpNemico;
    private final int mpNemico;
    private final String displayName;


    Skill(String displayName, int hpPersonaggio, int mpPersonaggio, int hpNemico, int mpNemico) {
        this.displayName = displayName;
        this.hpPersonaggio = hpPersonaggio;
        this.mpPersonaggio = mpPersonaggio;
        this.hpNemico = hpNemico;
        this.mpNemico = mpNemico;
    }

    public int getHpPersonaggio() {
        return hpPersonaggio;
    }

    public int getMpPersonaggio() {
        return mpPersonaggio;
    }

    public int getHpNemico() {
        return hpNemico;
    }

    public int getMpNemico() {
        return mpNemico;
    }

    @Override
    public String toString () {
        return getDisplayName();
    }

    public String getDisplayName() {
        return displayName;
    }



}

