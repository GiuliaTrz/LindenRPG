package it.unicam.cs.mpgc.rpg126763.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public interface SaveManager {
    /**
     * Salva un oggetto {@link SaveData} su file.
     * @param data     dati da salvare
     * @throws SaveLoadException se il salvataggio fallisce
     */
    void save(SaveData data) throws SaveLoadException;

    /**
     * Carica un file di salvataggio e ripristina i massimi delle statistiche.
     * @return i dati caricati
     * @throws SaveLoadException se il file non esiste o è illeggibile
     */
    public SaveData load() throws SaveLoadException;
}
