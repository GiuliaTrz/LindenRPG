package it.unicam.cs.mpgc.rpg126763.models;

/**
 * Rappresenta l'equipaggiamento (arma) che può essere associato a un personaggio.
 * Attualmente non è ancora utilizzato nel gameplay, ma la struttura è predisposta
 * per future estensioni del sistema di combattimento e di gestione dell'inventario.
 */
public enum Equip {
    SWORD("Spada"),
    BOW("Arco"),
    STAFF("Bastone Magico");

    private final String nome;

    Equip(String nome) {

        this.nome = nome;
    }

    public String getNome() {

        return nome;
    }

    @Override
    public String toString() {

        return nome;
    }
}