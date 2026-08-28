package it.unicam.cs.mpgc.rpg126763.battle;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import java.util.List;

/**
 * Factory per la creazione di nemici predefiniti utilizzati nella demo.
 * Fornisce un metodo statico che restituisce una lista di nemici base,
 * ciascuno con nome, punti vita, attacco e percorso dell'immagine associata.
 *
 * <p>Questa classe permette di centralizzare la definizione dei nemici
 * e semplifica l'eventuale aggiunta di nuovi nemici in futuro.</p>
 */
public class EnemyFactory {

    /**
     * Crea e restituisce una lista di nemici predefiniti per la demo.
     * I nemici inclusi attualmente (nella versione demo)
     * sono quattro slime elementali: acqua, fuoco, vento e terra.
     *
     * @return una lista immutabile di oggetti {@link Nemico}.
     */
    public static List<Nemico> createDefaultEnemies() {
        return List.of(
                new Nemico("Water Slime", 50, 15, "/it/unicam/cs/mpgc/rpg126763/images/water_slime.png"),
                new Nemico("Fire Slime", 40, 20, "/it/unicam/cs/mpgc/rpg126763/images/fire_slime.png"),
                new Nemico("Wind Slime", 70, 15, "/it/unicam/cs/mpgc/rpg126763/images/wind_slime.png"),
                new Nemico("Earth Slime", 100, 10, "/it/unicam/cs/mpgc/rpg126763/images/earth_slime.png")
        );
    }
}