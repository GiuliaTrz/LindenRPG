package it.unicam.cs.mpgc.rpg126763.character;

import it.unicam.cs.mpgc.rpg126763.models.Equip;
import it.unicam.cs.mpgc.rpg126763.models.Item;
import it.unicam.cs.mpgc.rpg126763.models.MageType;
import it.unicam.cs.mpgc.rpg126763.models.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il personaggio controllato dal giocatore.
 * Estende {@link CombatCharacter} aggiungendo la gestione del tipo di mago,
 * dell'arma equipaggiata e dell'inventario.
 *
 * <p>Il tipo di mago determina le statistiche base e le abilità disponibili.
 * L'arma e l'inventario sono attualmente predisposti per future estensioni
 * del gameplay, ma non ancora utilizzati nella demo.</p>
 */
public class Personaggio extends CombatCharacter {
    private MageType mageType;
    private Equip weapon;
    private List<Item> inventory = new ArrayList<>();

    /**
     * Costruttore vuoto, utile per la deserializzazione.
     */
    public Personaggio() {}

    /**
     * Crea un personaggio con il nome specificato e statistiche di default.
     *
     * @param name il nome del personaggio
     */
    public Personaggio(String name) {
        super(name, 100, 50);
    }

    /**
     * Crea un personaggio con nome, statistiche di default e immagine associata.
     *
     * @param name      il nome del personaggio
     * @param imagePath il percorso dell'immagine rappresentativa
     */
    public Personaggio(String name, String imagePath) {
        super(name, 100, 50, imagePath);
    }

    /**
     * Restituisce il tipo di mago attualmente assegnato al personaggio.
     *
     * @return il tipo di mago, o {@code null} se non ancora impostato
     */
    public MageType getMageType() { return mageType; }


    /**
     * Imposta il tipo di mago del personaggio e aggiorna HP e MP massimi
     * in base alle statistiche del tipo scelto.
     *
     * @param mageType il tipo di mago da assegnare
     */
    public void setMageType(MageType mageType) {
        this.mageType = mageType;
        this.hp = mageType.getBaseHp();
        this.mp = mageType.getBaseMp();
        this.maxHp = this.hp;
        this.maxMp = this.mp;
    }

    /**
     * Restituisce le abilità disponibili per il tipo di mago corrente.
     * Se il tipo di mago non è impostato, restituisce una lista vuota.
     *
     * @return una lista di {@link Skill}
     */
    public List<Skill> getSkills() {
        return mageType != null ? mageType.getSkills() : List.of();
    }

    /**
     * Restituisce una rappresentazione testuale del personaggio includendo
     * tipo di mago ed eventuale arma equipaggiata.
     *
     * @return una stringa descrittiva del personaggio
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (mageType != null) sb.append(" - ").append(mageType.getName());
        if (weapon != null) sb.append(" [").append(weapon).append("]");
        return sb.toString();
    }

    // Metodi per aggiornamenti futuri

    /**
     * Restituisce la lista degli oggetti nell'inventario.
     *
     * @return la lista degli oggetti
     */
    public List<Item> getInventory() { return inventory; }

    /**
     * Restituisce l'arma attualmente equipaggiata.
     *
     * @return l'arma equipaggiata, o {@code null} se non presente
     */
    public Equip getWeapon() { return weapon; }

    /**
     * Equipaggia un'arma sul personaggio.
     *
     * @param weapon l'arma da equipaggiare
     */
    public void setWeapon(Equip weapon) { this.weapon = weapon; }

    /**
     * Aggiunge un oggetto all'inventario.
     *
     * @param item l'oggetto da aggiungere
     */
    public void addItem(Item item) { inventory.add(item); }

    /**
     * Rimuove un oggetto dall'inventario.
     *
     * @param item l'oggetto da rimuovere
     */
    public void removeItem(Item item) { inventory.remove(item); }

    /**
     * Restituisce l'oggetto in una determinata posizione dell'inventario.
     *
     * @param index l'indice dell'oggetto
     * @return l'oggetto all'indice specificato
     */
    public Item getItem(int index) { return inventory.get(index); }

    /**
     * Utilizza un oggetto dell'inventario applicandone l'effetto in base al tipo.
     * L'oggetto viene rimosso dopo l'uso.
     *
     * @param item l'oggetto da utilizzare
     */

    public void useItem(Item item) {
        if (item == null) return;
        switch (item.getType()) {
            case HEAL -> heal(item.getValue());
            case BUFF -> mp = Math.min(maxMp, mp + item.getValue());
            case QUEST -> { }
        }
        inventory.remove(item);
    }
}