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
    public Personaggio(String name, String imagePath) {
        super(name, 100, 50, imagePath);
    }


    public MageType getMageType() { return mageType; }
    public Equip getWeapon() { return weapon; } //eventuale estensione futura
    public List<Item> getInventory() { return inventory; }

    public void setMageType(MageType mageType) {
        this.mageType = mageType;
        this.hp = mageType.getBaseHp();
        this.mp = mageType.getBaseMp();
        this.maxHp = this.hp;
        this.maxMp = this.mp;
    }

    public List<Skill> getSkills() {
        return mageType != null ? mageType.getSkills() : List.of();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (mageType != null) sb.append(" - ").append(mageType.getName());
        if (weapon != null) sb.append(" [").append(weapon).append("]");
        return sb.toString();
    }


    // lascio questi metodi per eventuali estensioni future

    public void setWeapon(Equip weapon) { this.weapon = weapon; }
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

}