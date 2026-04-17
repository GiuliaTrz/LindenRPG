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

    // costruttore vuoto per JSON (da aggiungere)
    public Nemico() {}

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp = Math.max(0, hp - dmg); // evita HP negativi
        }
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

    @Override
    public String toString() {
        return "Nemico{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                '}';
    }
}