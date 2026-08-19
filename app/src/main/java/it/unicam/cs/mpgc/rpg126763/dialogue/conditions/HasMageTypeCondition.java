package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.MageType;

/**
 * Verifica che il giocatore abbia un determinato tipo di mago.
 */
public class HasMageTypeCondition implements DialogueCondition {
    private final MageType requiredType;

    public HasMageTypeCondition(MageType requiredType) {
        this.requiredType = requiredType;
    }

    @Override
    public boolean check(Personaggio player) {
        return player.getMageType() == requiredType;
    }
}