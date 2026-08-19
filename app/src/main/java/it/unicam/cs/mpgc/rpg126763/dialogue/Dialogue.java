package it.unicam.cs.mpgc.rpg126763.dialogue;

import java.util.List;

/**
 * nodo per il dialogo, con uno "speaker" opzionale e lista di opzioni
 */
public class Dialogue {
    private String id;
    private String text;
    private String speaker;       // opzionale
    private List<Option> options;
    public void setText(String text) {
        this.text = text;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
    public String getId() { return id; }
    public String getText() { return text; }
    public String getSpeaker() { return speaker; }
    public List<Option> getOptions() { return options; }
    public boolean hasOptions() { return options != null && !options.isEmpty(); }
}