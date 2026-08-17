package it.unicam.cs.mpgc.rpg126763.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;

import java.io.FileReader;
import java.io.FileWriter;

public class SaveManager {

    private final Gson gson;

    public SaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

   //save
    public void save(String filePath, Personaggio player) {

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(player, writer);
            System.out.println("Partita salvata!");
        } catch (Exception e) {
            System.out.println("Errore salvataggio: " + e.getMessage());
        }
    }

    //Load
    public Personaggio load(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            Personaggio player = gson.fromJson(reader, Personaggio.class);
            System.out.println("Partita caricata!");
            return player;

        } catch (Exception e) {
            System.out.println("Errore caricamento: " + e.getMessage());
            return null;
        }
    }
}