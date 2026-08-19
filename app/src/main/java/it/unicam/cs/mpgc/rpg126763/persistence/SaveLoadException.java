package it.unicam.cs.mpgc.rpg126763.persistence;

/**
 * Eccezione personalizzata per errori di salvataggio o caricamento.
 * Viene lanciata da {@link JsonSaveManager} e gestita dal chiamante.
 */
public class SaveLoadException extends Exception {
    public SaveLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}