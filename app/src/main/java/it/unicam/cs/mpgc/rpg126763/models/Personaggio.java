package it.unicam.cs.mpgc.rpg126763.models;


import java.util.ArrayList;
import java.util.List;


public class Personaggio {
    private String name;
    private int hp;
    private int mp;
    private Equip weapon;

    private List<Item> inventory = new ArrayList<>();

    public Personaggio(String name) {
        this.name = name;
        this.hp = 100;
        this.mp = 50;
    }

    public Personaggio() {} //Metodo vuoto per la persistenza

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }

    public void setWeapon(Equip weapon) {
        this.weapon = weapon;
    }

    public Equip getWeapon() {
        return weapon;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
