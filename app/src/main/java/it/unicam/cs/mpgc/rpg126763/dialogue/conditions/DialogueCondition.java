package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Condizione per mostrare/nascondere un'opzione di dialogo.
 */
public interface DialogueCondition {
    boolean check(Personaggio player);
}