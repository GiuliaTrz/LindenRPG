package it.unicam.cs.mpgc.rpg126763.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

/**
 * Gestisce la persistenza su file JSON.
 * Utilizza la libreria Gson per serializzare/deserializzare oggetti {@link SaveData}.
 *
 * Non gestisce le eccezioni internamente: le propaga come {@link SaveLoadException}
 * in modo che il chiamante (es. GameEngine) possa decidere come comportarsi.
 */
public class JsonSaveManager implements SaveManager {
    private final Gson gson;
    private String filePath;
//save manager: interfaccia con save e load e senza file path sui metodi
    //json save manager implementa interfaccia e nel costruttore passo il file e implemento metodi
    public JsonSaveManager(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Salva un oggetto {@link SaveData} su file.
     * @param data     dati da salvare
     * @throws SaveLoadException se il salvataggio fallisce (es. errore di I/O)
     */
    public void save(SaveData data) throws SaveLoadException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new SaveLoadException("Salvataggio fallito: " + filePath, e);
        }
    }

    /**
     * Carica un file di salvataggio e ripristina i massimi delle statistiche.
     * @return i dati caricati
     * @throws SaveLoadException se il file non esiste o è illeggibile
     */
    public SaveData load() throws SaveLoadException {
        try (FileReader reader = new FileReader(filePath)) {
            SaveData data = gson.fromJson(reader, SaveData.class);
            if (data != null && data.getPlayer() != null) {
                // Dopo la deserializzazione, i massimali potrebbero essere errati:
                // li ricalcoliamo per coerenza.
                data.getPlayer().recalculateMaxStats();
            }
            return data;
        } catch (IOException e) {
            throw new SaveLoadException("Caricamento fallito: " + filePath, e);
        }
    }
}