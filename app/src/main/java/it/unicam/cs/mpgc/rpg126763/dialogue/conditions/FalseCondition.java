package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Condizione che è sempre falsa.
 * Utile per test o per nascondere opzioni temporaneamente.
 */
public class FalseCondition implements DialogueCondition {

    @Override
    public boolean check(Personaggio player) {
        return false;
    }
}