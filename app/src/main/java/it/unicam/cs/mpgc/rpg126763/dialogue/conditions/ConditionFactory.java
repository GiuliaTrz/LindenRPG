package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.models.MageType;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory che associa chiavi testuali a condizioni di dialogo concrete.
 * Utilizzata per recuperare una {@link DialogueCondition} a partire da una stringa
 * presente nei dati dei dialoghi (ad esempio nel JSON).
 *
 * <p>Le condizioni disponibili sono registrate all'interno della mappa interna
 * e possono essere estese aggiungendo nuove voci nel costruttore.</p>
 */
public class ConditionFactory {

    private final Map<String, DialogueCondition> conditions = new HashMap<>();

    /**
     * Costruisce la factory registrando le condizioni predefinite per la demo.
     */
    public ConditionFactory() {
        conditions.put("IS_EMBERWEAVER", new HasMageTypeCondition(MageType.EMBERWEAVER));
        conditions.put("IS_TIDEWEAVER", new HasMageTypeCondition(MageType.TIDEWEAVER));
        conditions.put("IS_GUSTWEAVER", new HasMageTypeCondition(MageType.GUSTWEAVER));
        conditions.put("IS_EARTHWEAVER", new HasMageTypeCondition(MageType.EARTHWEAVER));
        conditions.put("HAS_CRYSTAL", new HasItemCondition("Cristallo Antico"));
    }

    /**
     * Restituisce la condizione associata alla chiave specificata.
     *
     * @param key la chiave della condizione
     * @return la condizione corrispondente, oppure {@code null} se non esiste
     */
    public DialogueCondition get(String key) {
        return conditions.get(key);
    }
}