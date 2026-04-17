package it.unicam.cs.mpgc.rpg126763.models;

public class Item {

    private String name;
    private String description;
    private ItemType type;

    // valore opzionale (es: cura HP)
    private int value;

    public Item() {}

    public Item(String name, String description, ItemType type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + description;
    }
}