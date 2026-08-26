package it.unicam.cs.mpgc.rpg126763.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteSaveController {

    @FXML
    private ListView<File> saveListView;   // Mostra i file .json

    @FXML
    private Button deleteButton;

    private static final String SAVE_DIRECTORY = "saves/";

    private final ObservableList<File> saveFiles = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        saveListView.setItems(saveFiles);
        loadSaveFiles();
    }

    /**
     * Legge tutti i file .json dalla cartella dei salvataggi e li aggiunge alla lista.
     */
    private void loadSaveFiles() {
        File dir = new File(SAVE_DIRECTORY);
        if (!dir.exists() || !dir.isDirectory()) {
            showAlert("Cartella non trovata", "La cartella dei salvataggi non esiste: " + SAVE_DIRECTORY);
            return;
        }

        try (Stream<Path> paths = Files.list(Paths.get(SAVE_DIRECTORY))) {
            List<File> jsonFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".json"))
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::getName))
                    .collect(Collectors.toList());

            saveFiles.setAll(jsonFiles);
        } catch (IOException e) {
            showAlert("Errore di lettura", "Impossibile leggere i file dalla cartella: " + e.getMessage());
        }
    }

    /**
     * Gestisce il click sul bottone Elimina: cancella il file selezionato.
     */
    @FXML
    private void handleDeleteAction() {
        File selectedFile = saveListView.getSelectionModel().getSelectedItem();
        if (selectedFile == null) {
            showAlert("Nessun file selezionato", "Seleziona prima un salvataggio dalla lista.");
            return;
        }

        boolean deleted = selectedFile.delete();
        if (deleted) {
            saveFiles.remove(selectedFile);
            showAlert("File eliminato", "Il salvataggio è stato eliminato con successo.");
        } else {
            showAlert("Errore", "Impossibile eliminare il file: " + selectedFile.getName());
        }
    }

    /**
     * Mostra una finestra di dialogo con un messaggio.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
