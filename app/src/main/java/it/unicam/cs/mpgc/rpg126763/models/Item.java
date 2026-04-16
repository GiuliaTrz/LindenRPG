package it.unicam.cs.mpgc.rpg126763.models;

public class Item {
    private String name;
    private String description;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item() {} // needed for JSON (Gson)

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
