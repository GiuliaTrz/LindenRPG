package it.unicam.cs.mpgc.rpg126763.character;

import it.unicam.cs.mpgc.rpg126763.models.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il personaggio controllato dal giocatore e
 * estende {@link CombatCharacter} con tipo di mago (la classe del giocatore), arma, inventario e skill.
 */
public class Personaggio extends CombatCharacter {
    private MageType mageType;
    private Equip weapon;
    private List<Item> inventory = new ArrayList<>();

    /** Costruttore vuoto per Gson */
    public Personaggio() {}

    /**
     * Crea un nuovo personaggio con HP e MP base.
     * @param name nome del personaggio
     */
    public Personaggio(String name) {
        super(name, 100, 50);
    }

    public MageType getMageType() { return mageType; }
    public Equip getWeapon() { return weapon; }
    public List<Item> getInventory() { return inventory; }

    /**
     * Imposta il tipo di mago(classe) e ricalcola HP/MP massimi in base al tipo.
     * @param mageType tipo di mago
     */
    public void setMageType(MageType mageType) {
        this.mageType = mageType;
        this.hp = mageType.getBaseHp();
        this.mp = mageType.getBaseMp();
        this.maxHp = this.hp;
        this.maxMp = this.mp;
    }

    public void setWeapon(Equip weapon) { this.weapon = weapon; }

    /**
     * Restituisce le skill disponibili a seconda della classe del personaggio
     * @return lista di skill (vuota se nessun mago scelto)
     */
    public List<Skill> getSkills() {
        return mageType != null ? mageType.getSkills() : List.of();
    }

    // Gestione dell'inventario
    public void addItem(Item item) { inventory.add(item); }
    public void removeItem(Item item) { inventory.remove(item); }
    public Item getItem(int index) { return inventory.get(index); }

    /**
     * Usa un oggetto dall'inventario, applicandone l'effetto (cura, buff, quest)
     * e rimuovendolo.
     * @param item oggetto da usare (se null non fa nulla)
     */
    public void useItem(Item item) {
        if (item == null) return;
        switch (item.getType()) {
            case HEAL -> heal(item.getValue());
            case BUFF -> mp = Math.min(maxMp, mp + item.getValue());
            case QUEST -> { /* eventuale gestione quest */ }
        }
        inventory.remove(item);
    }

    /** Stampa l'inventario a console. */
    public void printInventory() {
        System.out.println("\n Inventario:");
        if (inventory.isEmpty()) {
            System.out.println("(vuoto)");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ") " + inventory.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (mageType != null) sb.append(" - ").append(mageType.getName());
        if (weapon != null) sb.append(" [" + weapon + "]");
        return sb.toString();
    }
}