package it.unicam.cs.mpgc.rpg126763.character;

import it.unicam.cs.mpgc.rpg126763.models.Equip;
import it.unicam.cs.mpgc.rpg126763.models.Item;
import it.unicam.cs.mpgc.rpg126763.models.MageType;
import it.unicam.cs.mpgc.rpg126763.models.Skill;

import java.util.ArrayList;
import java.util.List;

public class Personaggio extends CombatCharacter {
    private MageType mageType;
    private Equip weapon;
    private List<Item> inventory = new ArrayList<>();

    public Personaggio() {}
    public Personaggio(String name) {
        super(name, 100, 50);
    }

    public MageType getMageType() { return mageType; }
    public Equip getWeapon() { return weapon; }
    public List<Item> getInventory() { return inventory; }

    public void setMageType(MageType mageType) {
        this.mageType = mageType;
        this.hp = mageType.getBaseHp();
        this.mp = mageType.getBaseMp();
        this.maxHp = this.hp;
        this.maxMp = this.mp;
    }

    public void setWeapon(Equip weapon) { this.weapon = weapon; }

    public List<Skill> getSkills() {
        return mageType != null ? mageType.getSkills() : List.of();
    }

    public void addItem(Item item) { inventory.add(item); }
    public void removeItem(Item item) { inventory.remove(item); }
    public Item getItem(int index) { return inventory.get(index); }

    public void useItem(Item item) {
        if (item == null) return;
        switch (item.getType()) {
            case HEAL -> heal(item.getValue());
            case BUFF -> mp = Math.min(maxMp, mp + item.getValue());
            case QUEST -> { }
        }
        inventory.remove(item);
    }

    public void printInventory() {
        System.out.println("\nInventario:");
        if (inventory.isEmpty()) {
            System.out.println("(vuoto)");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ") " + inventory.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (mageType != null) sb.append(" - ").append(mageType.getName());
        if (weapon != null) sb.append(" [").append(weapon).append("]");
        return sb.toString();
    }


}