package it.unicam.cs.mpgc.rpg126763.models;

/**
 * Rappresenta le possibili categorie di oggetti utilizzabili nel gioco.
 * Ogni tipo determina l'effetto che l'oggetto ha quando viene utilizzato dal giocatore.
 */
public enum ItemType {
    /** Oggetto in grado di ripristinare punti vita (HP). */
    HEAL,

    /** Oggetto in grado di ripristinare punti magia (MP). */
    BUFF,

    /** Oggetto legato a una missione o a un obiettivo, senza effetto immediato. */
    QUEST
}