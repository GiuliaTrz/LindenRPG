package it.unicam.cs.mpgc.rpg126763.models;


import java.util.ArrayList;
import java.util.List;


public class Personaggio {
    private String name;
    private int hp;
    private int mp;
    private Equip weapon;
    private List<Item> inventory = new ArrayList<>();

    //json utile per estendibilità

    public Personaggio(String name) {
        this.name = name;
        this.hp = 100;
        this.mp = 50;
    }

    /**
     * Uso il design pattern Builder per massimizzare l'estendibilità e gestire facilmente
     * la visualizzazione delle statistiche
     * @return ritorna le informazioni del personaggio (nome, hp, mp)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name:").append(name).append("\n");
        sb.append("Hp:").append(hp).append("\n");
        sb.append("Mp:").append(mp).append("\n");
        sb.append("Arma:").append(weapon).append("\n");
        return sb.toString();
    }

    /**
     * Creo un metodo vuoto per la persistenza
     */
    public Personaggio() {}

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }

    public void setWeapon(Equip weapon) {
        this.weapon = weapon;
        this.hp = weapon.getBaseHp();
        this.mp = weapon.getBaseMp();  //aggiorno gli mp a quelli corrispondenti alla classe
    }

    public Equip getWeapon() {
        return weapon;
    }

    public void takeDamage(int dmg) {
        if (dmg>=0) {
            hp -= dmg;
        }
    }

    public void heal(int hp){
        if (hp>=0){
            this.hp += hp;
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
