package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.battle.EnemyFactory;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller per la finestra principale del menu.
 * Gestisce la creazione di una nuova partita, il caricamento dei salvataggi
 * e la preparazione dell'interfaccia di gioco.
 */
public class MainWindowController {

    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    private Stage primaryStage;
    private GameEngine gameEngine;
    private List<Nemico> enemies;
    private static final String SAVE_DIR = "saves/";
    private static final String STYLESHEET_PATH = "/it/unicam/cs/mpgc/rpg126763/css/game-style.css";
    private static final String GAME_FXML_PATH = "GameWindow.fxml";

    /**
     * Inizializza il controller, creando la cartella dei salvataggi
     * e i nemici predefiniti del gioco, se non già presenti.
     */
    @FXML
    public void initialize() {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
        } catch (IOException e) {
            System.err.println("Impossibile creare la cartella dei salvataggi: " + e.getMessage());
        }

        // Inizializzazione dei nemici
        enemies = EnemyFactory.createDefaultEnemies();
        updateLoadButtonState();
    }

    /**
     * Imposta lo stage principale su cui operare.
     *
     * @param primaryStage lo stage principale dell'applicazione.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Gestisce la creazione di una nuova partita chiedendo il nome del personaggio.
     * Se il nome è valido, prepara e avvia una nuova istanza del gioco.
     */
    @FXML
    private void handleNewGame() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Nuova partita");
        nameDialog.setHeaderText("Inserisci il nome del tuo personaggio. \nSono permessi solo caratteri alfanumerici, - e _. .");
        nameDialog.setContentText("Nome:");

        nameDialog.showAndWait().ifPresent(name -> {
            if (!name.matches("[A-Za-z0-9_-]+") || name.length()>50) {
                showAlert("Errore", "Il nome inserito non è valido!");
                return;
            }
            String cleanName = name.trim();
            String filePath = SAVE_DIR + cleanName.toLowerCase() + ".json";
            setupGameWindow(filePath, "LindenRPG - " + cleanName, true, cleanName);
        });
    }

    /**
     * Gestisce il caricamento di una partita esistente.
     * Mostra un dialog per selezionare il file di salvataggio e carica il gioco.
     */
    @FXML
    private void handleLoadGame() {
        List<String> saveFiles = getSaveFileNames();
        if (saveFiles.isEmpty()) {
            showAlert("Info", "Nessun salvataggio trovato!");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(saveFiles.get(0), saveFiles);
        dialog.setTitle("Carica partita");
        dialog.setHeaderText("Seleziona il salvataggio da caricare");
        dialog.setContentText("File disponibili:");

        dialog.showAndWait().ifPresent(selectedFileName -> {
            String filePath = SAVE_DIR + selectedFileName;
            setupGameWindow(filePath, "LindenRPG", false, null);
        });
    }

    private GameEngine createGameEngine(JavaFXUI ui, String filePath) {
        BattleManager battleManager = new BattleManager(ui);
        SaveManager saveManager = new JsonSaveManager(filePath);

        return new GameEngine(
                ui,
                enemies,
                saveManager,
                battleManager
        );
    }

    /**
     * Metodo helper unico per preparare e mostrare la finestra di gioco.
     *
     * @param filePath   Il percorso del file di salvataggio.
     * @param title      Il titolo della finestra.
     * @param isNewGame  {@code true} se si sta creando una nuova partita, {@code false} se si sta caricando.
     * @param playerName Il nome del giocatore (solo per nuova partita, altrimenti {@code null}).
     */
    private void setupGameWindow(String filePath, String title, boolean isNewGame, String playerName) {
        JavaFXUI ui = new JavaFXUI();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GAME_FXML_PATH));
            Parent root = loader.load();
            GameWindowController gameController = loader.getController();
            gameEngine = createGameEngine(ui, filePath);
            gameController.setUI(ui);
            gameController.setGameEngine(gameEngine);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            configureCloseRequest(gameEngine);
            primaryStage.show();
            if (isNewGame) {
                gameEngine.startNewGame(playerName);
            } else {
                gameEngine.loadGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore", "Impossibile caricare la schermata di gioco!");
        }
    }

    /**
     * Configura la richiesta di chiusura per chiedere se salvare prima di uscire.
     *
     * @param engine Il motore di gioco da salvare prima della chiusura.
     */
    private void configureCloseRequest(GameEngine engine) {
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
                engine.saveGame();
            } else {
                event.consume();
            }
        });
    }

    /**
     * Recupera la lista dei nomi dei file di salvataggio presenti nella directory.
     *
     * @return una lista ordinata di nomi di file .json, vuota se non ce ne sono.
     */
    private List<String> getSaveFileNames() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<>();
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(files)
                .map(File::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Aggiorna lo stato del pulsante "Carica partita" in base alla presenza di salvataggi.
     */
    private void updateLoadButtonState() {
        loadGameButton.setDisable(getSaveFileNames().isEmpty());
    }

    /**
     * Mostra un dialog informativo.
     *
     * @param title   Titolo del dialog.
     * @param message Messaggio da mostrare.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gestisce l'apertura della finestra per eliminare i file di salvataggio.
     *
     * @param actionEvent Evento che ha innescato l'azione.
     * @throws IOException Se il file FXML non può essere caricato.
     */
    public void handleSavedFiles(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedFilesWindow.fxml"));
        Parent root = loader.load();
        DeleteSaveController deleteSaveController = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Delete saved files");
        stage.show();
    }
}