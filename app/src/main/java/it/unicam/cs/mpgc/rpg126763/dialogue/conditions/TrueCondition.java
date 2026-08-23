package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Condizione che è sempre vera.
 * Utile per test o come default.
 */
public class TrueCondition implements DialogueCondition {

    @Override
    public boolean check(Personaggio player) {
        return true;
    }
}