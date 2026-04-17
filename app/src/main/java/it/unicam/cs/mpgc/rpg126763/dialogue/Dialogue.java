package it.unicam.cs.mpgc.rpg126763.dialogue;

import java.util.List;

public class Dialogue {

    private String id;
    private String text;
    private List<Option> options;

    public Dialogue() {}

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    @Override
    public String toString() {
        return text;
    }
}