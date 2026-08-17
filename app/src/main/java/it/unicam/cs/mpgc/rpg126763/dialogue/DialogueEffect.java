package it.unicam.cs.mpgc.rpg126763.dialogue;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

/**
 * Interfaccia per un effetto di dialogo.
 */
public interface DialogueEffect {
    /**
     * Applica l'effetto al personaggio.
     * @param player personaggio su cui agire
     */
    void apply(Personaggio player);

    /**
     * Restituisce una descrizione testuale dell'effetto (opzionale).
     * @return la descrizione, oppure null se non prevista
     */
    default String getDescription() {
        return null;
    }
}