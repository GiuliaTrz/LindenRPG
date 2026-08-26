package it.unicam.cs.mpgc.rpg126763.dialogue;

import java.util.List;

/**
 * Rappresenta un nodo di dialogo.
 * Contiene un testo, uno speaker opzionale, una lista di opzioni
 * e un flag che indica se dopo questo dialogo deve iniziare un combattimento.
 */
public class Dialogue {
    private String id;
    private String text;
    private String speaker;          // opzionale (può essere null)
    private String speakerDialogue;
    private List<Option> options;    // null o vuoto = nessuna scelta
    private boolean battle;          // true = dopo questo dialogo inizia un combattimento

    public Dialogue() {}

    public Dialogue(String id, String text, String speaker, List<Option> options, boolean battle) {
        this.id = id;
        this.text = text;
        this.speaker = speaker;
        this.options = options;
        this.battle = battle;
    }

    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }

    public String getText() {
        return text; }
    public void setText(String text) {
        this.text = text; }

    public String getSpeaker() {
        return speaker; }
    public void setSpeaker(String speaker) {
        this.speaker = speaker; }

    public String getSpeakerDialogue() {
        return speakerDialogue;
    }
    public void setSpeakerDialogue(String speakerDialogue) {
        this.speakerDialogue = speakerDialogue;
    }

    public List<Option> getOptions() {
        return options; }
    public void setOptions(List<Option> options) {
        this.options = options; }

    public boolean isBattle() {
        return battle; }
    public void setBattle(boolean battle) {
        this.battle = battle; }

    /**
     * Verifica se il nodo ha opzioni disponibili.
     * @return true se la lista delle opzioni non è null e non è vuota
     */
    public boolean hasOptions() {

        return options != null && !options.isEmpty();
    }

    @Override
    public String toString() {
        return "Dialogue{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", speaker='" + speaker + '\'' +
                ", options=" + options +
                ", battle=" + battle +
                '}';
    }
}