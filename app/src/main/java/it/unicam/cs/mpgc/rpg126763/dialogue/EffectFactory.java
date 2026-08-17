package it.unicam.cs.mpgc.rpg126763.dialogue;

import it.unicam.cs.mpgc.rpg126763.dialogue.effects.SetMageEffect;
import it.unicam.cs.mpgc.rpg126763.models.MageType;
import java.util.HashMap;
import java.util.Map;

/**
 * questa classe associa la chiave di effetto (dal JSON) all'implementazione
 */
public class EffectFactory {
    private final Map<String, DialogueEffect> effects = new HashMap<>();

    public EffectFactory() {
        effects.put("SET_MAGE_EMBER", new SetMageEffect(MageType.EMBERWEAVER));
        effects.put("SET_MAGE_TIDE", new SetMageEffect(MageType.TIDEWEAVER));
        effects.put("SET_MAGE_GUST", new SetMageEffect(MageType.GUSTWEAVER));
        effects.put("SET_MAGE_EARTH", new SetMageEffect(MageType.EARTHWEAVER));
    }

    public DialogueEffect get(String key) {
        return effects.get(key);
    }
}