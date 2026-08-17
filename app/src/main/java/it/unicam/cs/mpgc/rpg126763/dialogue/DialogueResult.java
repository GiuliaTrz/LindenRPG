package it.unicam.cs.mpgc.rpg126763.dialogue;

/**
 *Risultato dell'esecuzione di un dialogo, che contiene
 *eventualmente l'effetto scelto e l'ID dell'ultimo nodo raggiunto
 *
 * @param effectKey       chiave dell'effetto selezionato (o null)
 * @param lastDialogueId  ID dell'ultimo nodo visitato (checkpoint)
 */
public record DialogueResult(String effectKey, String lastDialogueId) {
}