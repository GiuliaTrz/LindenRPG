package it.unicam.cs.mpgc.rpg126763.models;

public class Nemico {
    private String name;
    private int hp;
    private int attack;

    public Nemico(String name, int hp, int attack) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }
}
