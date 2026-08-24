package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.models.MageType;

import java.util.HashMap;
import java.util.Map;

public class ConditionFactory {

    private final Map<String, DialogueCondition> conditions = new HashMap<>();

    public ConditionFactory() {
        conditions.put("IS_EMBERWEAVER", new HasMageTypeCondition(MageType.EMBERWEAVER));
        conditions.put("IS_TIDEWEAVER", new HasMageTypeCondition(MageType.TIDEWEAVER));
        conditions.put("IS_GUSTWEAVER", new HasMageTypeCondition(MageType.GUSTWEAVER));
        conditions.put("IS_EARTHWEAVER", new HasMageTypeCondition(MageType.EARTHWEAVER));
        conditions.put("HAS_CRYSTAL", new HasItemCondition("Cristallo Antico"));
    }

    public DialogueCondition get(String key) {
        return conditions.get(key);
    }
}