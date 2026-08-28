package it.unicam.cs.mpgc.rpg126763.dialogue;

/**
 * Rappresenta una singola opzione di scelta in un nodo di dialogo.
 * Può avere una condizione (per apparire solo se il giocatore soddisfa certi requisiti)
 * e un effetto (che modifica lo stato del giocatore).
 */
public class Option {
    private String id;
    private String text;
    private String nextDialogueId;
    private String effect;
    private String condition;


    /**
     * Costruttore vuoto, utile per la deserializzazione.
     */
    public Option() {}

    /**
     * Costruisce un'opzione con i dati specificati.
     *
     * @param id             identificativo univoco dell'opzione
     * @param text           testo mostrato al giocatore
     * @param nextDialogueId ID del nodo di dialogo successivo
     * @param effect         chiave dell'effetto da applicare (può essere null)
     * @param condition      chiave della condizione di visibilità (può essere null)
     */
    public Option(String id, String text, String nextDialogueId, String effect, String condition) {
        this.id = id;
        this.text = text;
        this.nextDialogueId = nextDialogueId;
        this.effect = effect;
        this.condition = condition;
    }

    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }

    public String getText() {
        return text; }
    public void setText(String text) {
        this.text = text; }

    public String getNextDialogueId() {
        return nextDialogueId; }
    public void setNextDialogueId(String nextDialogueId) {
        this.nextDialogueId = nextDialogueId; }

    public String getEffect() {
        return effect; }
    public void setEffect(String effect) {
        this.effect = effect; }

    public String getCondition() {
        return condition; }
    public void setCondition(String condition) {
        this.condition = condition; }

    @Override
    public String toString() {
        return "Option{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", nextDialogueId='" + nextDialogueId + '\'' +
                ", effect='" + effect + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}