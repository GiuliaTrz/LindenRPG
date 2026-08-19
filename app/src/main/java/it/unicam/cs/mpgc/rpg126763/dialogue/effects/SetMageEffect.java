package it.unicam.cs.mpgc.rpg126763.dialogue.effects;

import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.MageType;

/**
 * E' un effetto che imposta il tipo di mago del giocatore
 */
public class SetMageEffect implements DialogueEffect {
    private final MageType type;

    public SetMageEffect(MageType type) {
        this.type = type;
    }

    @Override
    public void apply(Personaggio player) {
        player.setMageType(type);
        //qui nessuna stampa, metto la descrizione altrove
    }

    /**
     * Ritorna il testo di atmosfera associato al tipo di mago
     */
    @Override
    public String getDescription() {
        return type.getFlavorText();
    }
}