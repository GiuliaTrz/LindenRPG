package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainWindowController {

    @FXML
    private Button newGameButton;

    @FXML
    private Button loadGameButton;

    private Stage primaryStage;
    private GameEngine gameEngine;
    private List<Nemico> enemies;

    private static final String SAVE_DIR = "saves/";

    @FXML
    public void initialize() {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }

        enemies = new ArrayList<>();
        enemies.add(new Nemico("Water Slime", 50, 15));
        enemies.add(new Nemico("Fire Slime", 40, 20));
        enemies.add(new Nemico("Wind Slime", 70, 15));
        enemies.add(new Nemico("Earth Slime", 100, 10));

        updateLoadButtonState();
    }

    private void updateLoadButtonState() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            loadGameButton.setDisable(true);
            return;
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        loadGameButton.setDisable(files == null || files.length == 0);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleNewGame() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Nuova partita");
        nameDialog.setHeaderText("Inserisci il nome del tuo personaggio");
        nameDialog.setContentText("Nome:");

        nameDialog.showAndWait().ifPresent(name -> {
            if (name.trim().isEmpty()) {
                showAlert("Errore", "Il nome non puo essere vuoto!");
                return;
            }

            JavaFXUI ui = new JavaFXUI();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
                Parent root = loader.load();
                GameWindowController gameController = loader.getController();

                gameController.setJavaFXUI(ui);

                String saveFilePath = SAVE_DIR + name.trim().toLowerCase() + ".json";
                gameEngine = new GameEngine(ui, enemies, saveFilePath);
                gameController.setGameEngine(gameEngine);

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("LindenRPG - " + name.trim());

                primaryStage.setOnCloseRequest(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Vuoi davvero chiudere?",
                            ButtonType.YES,
                            ButtonType.NO
                    );

                    alert.setTitle("Conferma chiusura");
                    alert.setHeaderText(null);

                    Optional<ButtonType> choiceResult = alert.showAndWait();

                    if (choiceResult.isPresent() && choiceResult.get() == ButtonType.YES) {
                       gameEngine.saveGame();
                    } else {
                        event.consume();
                    }
                });

                primaryStage.show();

                gameEngine.startNewGame(name.trim());

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Errore", "Impossibile caricare la schermata di gioco!");
            }
        });
    }

    @FXML
    private void handleLoadGame() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            showAlert("Info", "Nessun salvataggio trovato!");
            return;
        }

        File[] saveFiles = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (saveFiles == null || saveFiles.length == 0) {
            showAlert("Info", "Nessun salvataggio trovato!");
            return;
        }

        List<String> fileNames = new ArrayList<>();
        for (File f : saveFiles) {
            fileNames.add(f.getName());
        }

        javafx.scene.control.ChoiceDialog<String> dialog =
                new javafx.scene.control.ChoiceDialog<>(fileNames.get(0), fileNames);
        dialog.setTitle("Carica partita");
        dialog.setHeaderText("Seleziona il salvataggio da caricare");
        dialog.setContentText("File disponibili:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedFileName -> {
            String filePath = SAVE_DIR + selectedFileName;
            JavaFXUI ui = new JavaFXUI();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
                Parent root = loader.load();
                GameWindowController gameController = loader.getController();
                //collego la ui al controller
                gameController.setJavaFXUI(ui);

                gameEngine = new GameEngine(ui, enemies, filePath);
                gameController.setGameEngine(gameEngine);

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("LindenRPG");

                primaryStage.show();

                gameEngine.loadGame();

                primaryStage.setOnCloseRequest(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Vuoi davvero chiudere?",
                            ButtonType.YES,
                            ButtonType.NO
                    );

                    alert.setTitle("Conferma chiusura");
                    alert.setHeaderText(null);

                    Optional<ButtonType> choiceResult = alert.showAndWait();

                    if (choiceResult.isPresent() && choiceResult.get() == ButtonType.YES) {
                        gameEngine.saveGame();
                    } else {
                        event.consume();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Errore", "Impossibile caricare la schermata di gioco!");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}