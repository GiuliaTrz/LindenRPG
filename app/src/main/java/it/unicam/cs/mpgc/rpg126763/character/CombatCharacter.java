package it.unicam.cs.mpgc.rpg126763.character;

public abstract class CombatCharacter {
    protected String name;
    protected int hp;
    protected int mp;
    protected int maxHp;
    protected int maxMp;

    protected CombatCharacter() {}

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

    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp = Math.max(0, hp - dmg);
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            hp = Math.min(maxHp, hp + amount);
        }
    }

    public void consumeMp(int amount) {
        if (amount > 0) {
            mp = Math.max(0, mp - amount);
        }
    }


    @Override
    public String toString() {
        return String.format("%s [HP:%d/%d MP:%d/%d]", name, hp, maxHp, mp, maxMp);
    }
}