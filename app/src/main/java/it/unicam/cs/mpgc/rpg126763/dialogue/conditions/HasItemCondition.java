package it.unicam.cs.mpgc.rpg126763.dialogue.conditions;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Verifica che il giocatore possieda un certo oggetto nell'inventario.
 */
public class HasItemCondition implements DialogueCondition {

    private final String itemName;

    /**
     * Crea una nuova condizione che verifica il possesso di un oggetto.
     * @param itemName nome dell'oggetto da cercare nell'inventario
     */
    public HasItemCondition(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean check(Personaggio player) {
        return player.getInventory().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }
}