package it.unicam.cs.mpgc.rpg126763.models;

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