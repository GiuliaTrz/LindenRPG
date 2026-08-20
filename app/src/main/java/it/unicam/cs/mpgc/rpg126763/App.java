package it.unicam.cs.mpgc.rpg126763;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.ui.ConsoleUI;
import it.unicam.cs.mpgc.rpg126763.ui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        StackPane root = loader.load();

        MainWindowController controller = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setTitle("LindenRPG");
     //   scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
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