package it.unicam.cs.mpgc.rpg126763.persistence;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Container per i dati da salvare/caricare,
 * Include il personaggio, il nemico e l'ID dell'ultimo dialogo raggiunto (checkpoint raggiunto).
 */
public class SaveData {
    private Personaggio player;
    private Nemico enemy;
    private String lastDialogueId;

    /** Costruttore vuoto per Gson */
    public SaveData() {}

    public Nemico getEnemy() {
        return enemy;
    }

    public void setEnemy(Nemico enemy) {
        this.enemy = enemy;
    }

    /**
     * Crea un nuovo oggetto con i dati specificati.
     * @param player          personaggio da salvare
     * @param enemy            nemico da salvare
     * @param lastDialogueId  ID dell'ultimo nodo di dialogo raggiunto
     */
    public SaveData(Personaggio player, Nemico enemy, String lastDialogueId) {
        this.player = player;
        this.enemy = enemy;
        this.lastDialogueId = lastDialogueId;
    }

    public Personaggio getPlayer() {
        return player; }
    public String getLastDialogueId() {
        return lastDialogueId; }

    public void setPlayer(Personaggio player) {
        this.player = player; }
    public void setLastDialogueId(String lastDialogueId) {
        this.lastDialogueId = lastDialogueId; }
}