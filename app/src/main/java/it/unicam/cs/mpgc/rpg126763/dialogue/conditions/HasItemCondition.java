package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.dialogue.DialogueCondition;

/**
 * Verifica che il giocatore possieda un certo oggetto.
 */
public class HasItemCondition implements DialogueCondition {
    private final String itemName;

    public HasItemCondition(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean check(Personaggio player) {
        return player.getInventory().stream()
                .anyMatch(i -> i.getName().equals(itemName));
    }
}