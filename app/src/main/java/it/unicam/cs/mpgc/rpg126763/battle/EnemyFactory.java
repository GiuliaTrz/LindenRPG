package it.unicam.cs.mpgc.rpg126763.battle;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import java.util.List;

public class EnemyFactory {
    public static List<Nemico> createDefaultEnemies() {
        return List.of(new Nemico("Water Slime", 50, 15, "/it/unicam/cs/mpgc/rpg126763/images/water_slime.png"),
         new Nemico("Fire Slime", 40, 20, "/it/unicam/cs/mpgc/rpg126763/images/fire_slime.png"),
         new Nemico("Wind Slime", 70, 15, "/it/unicam/cs/mpgc/rpg126763/images/wind_slime.png"),
         new Nemico("Earth Slime", 100, 10, "/it/unicam/cs/mpgc/rpg126763/images/earth_slime.png")
        );
    }
}
