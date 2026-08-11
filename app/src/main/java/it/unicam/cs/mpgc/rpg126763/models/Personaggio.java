package it.unicam.cs.mpgc.rpg126763.models;

import java.util.ArrayList;
import java.util.List;
//scelgo classe astratta (ho tanti attributi da condividere e metodi che fanno sempre la stessa cosa)
//invece di usare un'interfaccia
//astratta= non posso istanziarla direttamente: astratta o no? astratta: serve per la gerarchia,
//posso aggiungere altri tipi di personaggi estendendo , quindi creando una classe che estende Personaggio
public class Personaggio {

    private String name;
    private int hp;
    private int mp;
    private MageType mageType;
    private Equip weapon;
    private List<Item> inventory = new ArrayList<>();

    public Personaggio(String name) {
        this.name = name;
        this.hp = 100;
        this.mp = 50;
    }

    public Personaggio() {}

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public MageType getMageType() {
        return mageType;
    }

    public Equip getWeapon() {
        return weapon;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void useItem(Item item) {

        if (item == null) return;

        switch (item.getType()) {

            case HEAL -> {
                heal(item.getValue());
                System.out.println("Hai recuperato" + item.getValue() + " HP!"); //togliere
            }

            case BUFF -> {
                mp += item.getValue();
                System.out.println("Ottieni " + item.getValue() + " MP!");
            }

            case QUEST -> {
                System.out.println("Hai utilizzato: " + item.getName());
            }
        }

        inventory.remove(item);
    }

    //setup iniziale del personaggio (scelta del tipo di mago: acqua, terra, aria, fuoco)
    public void setMageType(MageType mageType) {
        this.mageType = mageType;
        this.hp = mageType.getBaseHp();
        this.mp = mageType.getBaseMp();
    }

    public void setWeapon(Equip weapon) {
        this.weapon = weapon;
    }

    //logica di combattimento

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

    public void consumeMp(int amount) {
        if (amount > 0) {
            mp = Math.max(0, mp - amount);
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    //skills
    public List<Skill> getSkills() {
        if (mageType == null) {
            return List.of();
        }
        return mageType.getSkills();
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

    public void printInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventario vuoto.");
            return;
        }

        System.out.println("\n Inventario:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ") " + inventory.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name).append("\n");
        sb.append("HP: ").append(hp).append("\n");
        sb.append("MP: ").append(mp).append("\n");

        if (mageType != null) {
            sb.append("\nMage Type:\n").append(mageType).append("\n");
        }

        if (weapon != null) {
            sb.append("Weapon: ").append(weapon).append("\n");
        }

        sb.append("\nInventory:\n");
        for (Item i : inventory) {
            sb.append("- ").append(i).append("\n");
        }

        return sb.toString();
    }
}