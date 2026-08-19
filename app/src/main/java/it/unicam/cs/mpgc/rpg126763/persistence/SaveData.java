package it.unicam.cs.mpgc.rpg126763.persistence;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Container per i dati da salvare/caricare,
 * Include il personaggio e l'ID dell'ultimo dialogo raggiunto (checkpoint).
 */
public class SaveData {
    private Personaggio player;
    private String lastDialogueId;

    /** Costruttore vuoto per Gson */
    public SaveData() {}

    /**
     * Crea un nuovo oggetto con i dati specificati.
     * @param player          il personaggio da salvare
     * @param lastDialogueId  l'ID dell'ultimo nodo di dialogo raggiunto
     */
    public SaveData(Personaggio player, String lastDialogueId) {
        this.player = player;
        this.lastDialogueId = lastDialogueId;
    }

    public Personaggio getPlayer() { return player; }
    public String getLastDialogueId() { return lastDialogueId; }

    public void setPlayer(Personaggio player) { this.player = player; }
    public void setLastDialogueId(String lastDialogueId) { this.lastDialogueId = lastDialogueId; }
}