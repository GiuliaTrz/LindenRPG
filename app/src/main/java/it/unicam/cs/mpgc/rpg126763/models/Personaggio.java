package it.unicam.cs.mpgc.rpg126763.models;

import java.util.ArrayList;
import java.util.List;

public class Personaggio {

    private String name;
    private int hp;
    private int mp;

    private Classe classe;
    private Equip weapon;

    private List<Item> inventory = new ArrayList<>();

    public Personaggio(String name) {
        this.name = name;
        this.hp = 100;
        this.mp = 50;
    }

    //costruttore vuoto per JSON
    public Personaggio() {}


    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public Classe getClasse() { return classe; }
    public Equip getWeapon() { return weapon; }
    public List<Item> getInventory() { return inventory; }

    public void setClasse(Classe classe) {
        this.classe = classe;
        this.hp = classe.getBaseHp();
        this.mp = classe.getBaseMp();
    }

    public void setWeapon(Equip weapon) {
        this.weapon = weapon;
    }

    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp = Math.max(0, hp - dmg);
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            hp += amount;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

   //inventario
    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Item getItem(int index) {
        return inventory.get(index);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name).append("\n");
        sb.append("HP: ").append(hp).append("\n");
        sb.append("MP: ").append(mp).append("\n");

        if (classe != null) {
            sb.append("\nClasse:\n").append(classe).append("\n");
        }

        if (weapon != null) {
            sb.append("Arma: ").append(weapon).append("\n");
        }

        sb.append("\nInventario:\n");
        for (Item i : inventory) {
            sb.append("- ").append(i).append("\n");
        }

        return sb.toString();
    }
}