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
 * Classe responsabile del caricamento dei dialoghi da un file JSON presente nel classpath.
 * Il file deve trovarsi nella cartella delle risorse
 *
 * <p>Il formato del JSON è una lista di oggetti {@link Dialogue}, successivamente convertita
 * in una map che associa a ogni ID di dialogo il relativo nodo.</p>
 */
public class DialogueLoader {

    /**
     * Carica i dialoghi da una risorsa JSON e li restituisce come map ID → {@link Dialogue}.
     *
     * @param resourcePath percorso relativo alla cartella delle risorse (es. "dialogues.json")
     * @return una mappa che associa l'ID di ogni dialogo al nodo corrispondente
     * @throws RuntimeException se il file non esiste, se il parsing fallisce
     *                          o se sono presenti ID duplicati
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