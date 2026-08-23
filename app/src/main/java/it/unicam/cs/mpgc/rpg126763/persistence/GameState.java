package it.unicam.cs.mpgc.rpg126763.persistence;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Container per i dati da salvare/caricare,
 * Include il personaggio, il nemico e l'ID dell'ultimo dialogo raggiunto (checkpoint raggiunto).
 */
public class GameState {
    private Personaggio player;
    private Nemico enemy;
    private String currentDialogueId;

    /** Costruttore vuoto per Gson */
    public GameState() {}

    public Nemico getEnemy() {
        return enemy;
    }

    public void setEnemy(Nemico enemy) {
        this.enemy = enemy;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "player=" + player +
                ", enemy=" + enemy +
                ", currentDialogueId='" + currentDialogueId + '\'' +
                '}';
    }

    /**
     * Crea un nuovo oggetto con i dati specificati.
     * @param player          personaggio da salvare
     * @param enemy            nemico da salvare
     * @param currentDialogueId  ID dell'ultimo nodo di dialogo raggiunto
     */
    public GameState(Personaggio player, Nemico enemy, String currentDialogueId) {
        this.player = player;
        this.enemy = enemy;
        this.currentDialogueId= currentDialogueId;
    }

    public Personaggio getPlayer() {
        return player; }
    public String getCurrentDialogueId() {
        return currentDialogueId; }

    public void setPlayer(Personaggio player) {
        this.player = player; }
    public void setCurrentDialogueId(String currentDialogueId) {
        this.currentDialogueId = currentDialogueId; }
}