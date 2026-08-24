package it.unicam.cs.mpgc.rpg126763.persistence;

public interface SaveManager {
    /**
     * Salva un oggetto {@link GameState} su file.
     * @param data     dati da salvare
     * @throws SaveLoadException se il salvataggio fallisce
     */
    void save(GameState data) throws SaveLoadException;

    /**
     * Carica un file di salvataggio e ripristina i massimi delle statistiche.
     * @return i dati caricati
     * @throws SaveLoadException se il file non esiste o è illeggibile
     */
    public GameState load() throws SaveLoadException;
}
