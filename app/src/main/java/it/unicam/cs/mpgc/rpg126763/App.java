package it.unicam.cs.mpgc.rpg126763;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.ui.ConsoleUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principale (temporanea, da fixare) per l'esecuzione testuale da terminale di LindenRPG.
 *
 * Mostra un menu per la scelta tra nuova partita e caricamento,
 * quindi delega l'esecuzione al {@link GameEngine} tramite la UI console.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {

        launch();

       /*
        ConsoleUI ui = new ConsoleUI();
        Scanner scanner = new Scanner(System.in);

        ArrayList<Nemico> enemies = new ArrayList<>();
        enemies.add(new Nemico("Water slime", 50, 15));
        enemies.add(new Nemico("Fire slime", 40, 20));
        enemies.add(new Nemico("Wind slime", 70, 15));
        enemies.add(new Nemico("Earth slime", 100, 10));

        GameEngine engine = null;

        System.out.println("Dove vuoi salvare i dati di salvataggio?");
        String saveGamePath = scanner.nextLine();

        if (saveGamePath.isBlank()){
             engine = new GameEngine(ui, enemies);

        }
        else {
             engine = new GameEngine(ui, enemies, saveGamePath);
        }

        System.out.println("1) Nuova partita");
        System.out.println("2) Carica partita");
        System.out.print("Scelta > ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consuma newline

        if (choice == 2) {
            engine.loadGame();
        } else {
            System.out.print("Inserisci il nome del personaggio: ");
            String name = scanner.nextLine();
            engine.startNewGame(name.isBlank() ? "Linden" : name);
        }
         */


    }

}