package it.unicam.cs.mpgc.rpg126763.dialogue;

/**
 * Risultato dell'esecuzione di un dialogo.
 * Contiene l'effetto selezionato (se presente), l'ID dell'ultimo nodo visitato,
 * e un flag che indica se dopo questo dialogo deve iniziare un combattimento.
 */
public record DialogueResult(String effectKey, String lastDialogueId, boolean battle) {

    /**
     * Costruttore per compatibilità con codice che usa solo effectKey e lastDialogueId.
     * Imposta battle a false di default.
     */
    public DialogueResult(String effectKey, String lastDialogueId) {
        this(effectKey, lastDialogueId, false);
    }

    /**
     * Verifica se il risultato contiene un effetto valido.
     * @return true se effectKey non è null
     */
    public boolean hasEffect() {
        return effectKey != null && !effectKey.isBlank();
    }

    /**
     * Verifica se il risultato richiede l'avvio di un combattimento.
     * @return true se battle è true
     */
    public boolean isBattle() {
        return battle;
    }
}