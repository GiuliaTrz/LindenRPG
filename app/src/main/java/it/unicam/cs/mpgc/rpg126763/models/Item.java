package it.unicam.cs.mpgc.rpg126763.models;

/**
 * Rappresenta un oggetto generico che può essere raccolto o utilizzato dal giocatore.
 * Ogni oggetto ha un nome, una descrizione, un tipo e un valore numerico associato
 * (ad esempio, la quantità di HP da recuperare per gli oggetti di cura).
 */
public class Item {

    private String name;
    private String description;
    private ItemType type;
    private int value;

    public Item() {}

    public Item(String name, String description, ItemType type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public ItemType getType() { return type; }

    public int getValue() { return value; }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + description;
    }
}