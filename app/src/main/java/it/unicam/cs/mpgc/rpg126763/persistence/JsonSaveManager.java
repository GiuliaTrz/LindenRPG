package it.unicam.cs.mpgc.rpg126763.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

/**
 * Gestisce la persistenza su file JSON.
 * Utilizza la libreria Gson per serializzare/deserializzare oggetti {@link GameState}.
 *
 * Non gestisce le eccezioni internamente: le propaga come {@link SaveLoadException}
 * in modo che il chiamante (es. GameEngine) possa decidere come comportarsi.
 */
public class JsonSaveManager implements SaveManager {
    private final Gson gson;
    private String filePath;

    public JsonSaveManager(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Salva un oggetto {@link GameState} su file.
     * @param data     dati da salvare
     * @throws SaveLoadException se il salvataggio fallisce (es. errore di I/O)
     */
    public void save(GameState data) throws SaveLoadException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new SaveLoadException("Salvataggio fallito: " + filePath, e);
        }
    }

    /**
     * Carica un file di salvataggio ripristinando i dati
     * @return i dati caricati
     * @throws SaveLoadException se il file non esiste o è illeggibile
     */
    public GameState load() throws SaveLoadException {
        try (FileReader reader = new FileReader(filePath)) {
            GameState data = gson.fromJson(reader, GameState.class);
            return data;
        } catch (IOException e) {
            throw new SaveLoadException("Caricamento fallito: " + filePath, e);
        }
    }
}