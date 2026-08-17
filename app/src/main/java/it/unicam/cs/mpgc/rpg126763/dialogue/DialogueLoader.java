package it.unicam.cs.mpgc.rpg126763.dialogue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Carica i dialoghi dal file JSON nel classpath.
 */
public class DialogueLoader {

    /**
     * Carica i dialoghi da una risorsa JSON.
     * @param resourcePath percorso relativo alla cartella resources (es. "dialogues.json")
     * @return mappa id -> Dialogue
     * @throws RuntimeException se il file non esiste o il parsing fallisce
     */
    public Map<String, Dialogue> loadFromResource(String resourcePath) {
        Gson gson = new Gson();
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new RuntimeException("File non trovato: " + resourcePath);
        }
        InputStreamReader reader = new InputStreamReader(is);
        Type listType = new TypeToken<List<Dialogue>>(){}.getType();
        List<Dialogue> list = gson.fromJson(reader, listType);
        return list.stream().collect(Collectors.toMap(Dialogue::getId, d -> d));
    }
}