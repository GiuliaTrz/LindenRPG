package it.unicam.cs.mpgc.rpg126763.models;

public class NPC {

    private String name;

    @Override
    public String toString() {
        return "NPC{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public NPC (String name){
        this.name = name;
    }
}
