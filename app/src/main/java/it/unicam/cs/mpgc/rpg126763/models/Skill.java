package it.unicam.cs.mpgc.rpg126763.models;

public enum Skill {

    //memo cambiare nomi
    FIAMMA("Fiamma", 0, 3, 10, 0),
    COLPO_DEL_DRAGO("Colpo del Drago", 0, 5, 15, 0),
    SALTO_INFUOCATO("Salto Infuocato", 0, 8, 20, 0),

    FRECCIA_TRIPLA("Freccia Tripla", 0, 5, 20, 0),
    OCCHIO_DEL_FALCO("Occhio del Falco", 5, 5, 0, 0),
    COLPO_CRITICO("Colpo Critico", 0, 10, 25, 0),

    CATENE_INCANTATE("Catene Incantate", 0, 20, 10, 0),
    GUARIGIONE("Guarigione", 20, 10, 0, 0),
    SFERA_MAGICA("Sfera Magica", 0, 15, 5, 0);

    private final String displayName;

    // valori sempre POSITIVI → più chiaro
    private final int healPlayer;     // quanto cura il player
    private final int costMp;         // quanto MP consuma
    private final int damageEnemy;    // quanto danno fa al nemico
    private final int damageEnemyMp;  // eventuale danno MP al nemico (ma penso non serva)

    Skill(String displayName, int healPlayer, int costMp, int damageEnemy, int damageEnemyMp) {
        this.displayName = displayName;
        this.healPlayer = healPlayer;
        this.costMp = costMp;
        this.damageEnemy = damageEnemy;
        this.damageEnemyMp = damageEnemyMp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getHealPlayer() {
        return healPlayer;
    }

    public int getCostMp() {
        return costMp;
    }

    public int getDamageEnemy() {
        return damageEnemy;
    }

    public int getDamageEnemyMp() {
        return damageEnemyMp;
    }

    @Override
    public String toString() {
        return displayName;
    }
}