package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.CombatCharacter;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller della finestra di gioco.
 * Gestisce la logica di visualizzazione per le modalità dialogo, combattimento
 * e le scelte delle opzioni, delegando la logica di business al {@link GameEngine}.
 */
public class GameWindowController {

    private static final String IMAGES_DIR = "/it/unicam/cs/mpgc/rpg126763/images/";
    private static final String AVATAR_IMAGE = IMAGES_DIR + "avatar.png";
    private static final String GAME_SAVED_MESSAGE = "Partita salvata!";
    private static final String NO_GAME_MESSAGE = "Nessuna partita in corso!";
    private static final String NO_SKILL_MESSAGE = "Non hai skill disponibili!";

    @FXML
    private BorderPane dialogueMode;
    @FXML
    private Text speakerText;
    @FXML
    private TextArea dialogueTextArea;
    @FXML
    private BorderPane battleMode;
    @FXML
    private Label playerNameLabel;
    @FXML
    private ProgressBar playerHpBar;
    @FXML
    private Label playerHpLabel;
    @FXML
    private ProgressBar playerMpBar;
    @FXML
    private Label playerMpLabel;
    @FXML
    private Label enemyNameLabel;
    @FXML
    private ProgressBar enemyHpBar;
    @FXML
    private Label enemyHpLabel;
    @FXML
    private ImageView enemySprite;
    @FXML
    private HBox skillsContainer;
    @FXML
    private VBox optionsPanel;
    @FXML
    private ImageView speakerImage;

    private JavaFXUI ui;
    private GameEngine gameEngine;
    private StringBuilder historyBuilder = new StringBuilder();
    private boolean isBattleMode = false;

    /**
     * Inizializza il controller impostando la modalità dialogo iniziale.
     */
    @FXML
    public void initialize() {
        showDialogueMode();
    }

    /**
     * Imposta la UI e collega il controller.
     *
     * @param ui L'implementazione dell'interfaccia UI.
     */
    public void setUI(JavaFXUI ui) {
        this.ui = ui;
        ui.setController(this);
    }

    /**
     * Imposta il motore di gioco per gestire il salvataggio.
     *
     * @param gameEngine Il motore di gioco.
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Passa alla modalità dialogo, nascondendo battaglia e pannello opzioni.
     */
    public void showDialogueMode() {
        isBattleMode = false;
        toggleMode(dialogueMode, true);
        toggleMode(battleMode, false);
        optionsPanel.setVisible(false);
    }

    /**
     * Passa alla modalità battaglia, nascondendo dialogo e pannello opzioni.
     */
    public void showBattleMode() {
        isBattleMode = true;
        toggleMode(dialogueMode, false);
        toggleMode(battleMode, true);
        optionsPanel.setVisible(false);
    }

    /**
     * Aggiunge un messaggio di dialogo.
     * Se il gioco è in battaglia, passa automaticamente alla modalità dialogo.
     *
     * @param text    Il testo del messaggio.
     * @param speaker Il nome del parlante (può essere null).
     */
    public void appendDialogueText(String text, String speaker) {
        Platform.runLater(() -> {
            if (isBattleMode) {
                showDialogueMode();
            }
            speakerText.setText((speaker != null && !speaker.isBlank()) ? speaker : "");
            dialogueTextArea.appendText(text + "\n\n");
            historyBuilder.append((speaker != null && !speaker.isBlank()) ? speaker + ": " : "");
            historyBuilder.append(text).append("\n");
        });
    }

    /**
     * Aggiunge un messaggio di dialogo senza parlante specifico.
     *
     * @param text Il testo del messaggio.
     */
    public void appendDialogueText(String text) {
        appendDialogueText(text, null);
    }

    /**
     * Mostra le opzioni di scelta all'utente.
     * I bottoni vengono generati dinamicamente e disabilitati dopo la scelta.
     *
     * @param prompt  Il messaggio da mostrare sopra le opzioni.
     * @param options La lista delle opzioni da mostrare.
     * @param future  Il future da completare con l'indice scelto.
     */
    public void showOptions(String prompt, List<String> options, CompletableFuture<Integer> future) {
        Platform.runLater(() -> {
            if (isBattleMode) {
                showDialogueMode();
            }

            optionsPanel.getChildren().clear();
            optionsPanel.getChildren().add(createPromptText(prompt));

            for (int i = 0; i < options.size(); i++) {
                final int index = i;
                Button btn = new Button(options.get(i));
                btn.setOnAction(e -> {
                    future.complete(index);
                    disableButtons(optionsPanel);
                });
                optionsPanel.getChildren().add(btn);
            }
            optionsPanel.setVisible(true);
        });
    }

    /**
     * Mostra la scelta delle skill in battaglia.
     *
     * @param player Il personaggio del giocatore.
     * @param future Il future da completare con la skill scelta.
     */
    public void showSkillChoice(Personaggio player, CompletableFuture<Skill> future) {
        List<Skill> skills = player.getSkills();
        if (skills.isEmpty()) {
            appendDialogueText(NO_SKILL_MESSAGE);
            future.complete(null);
            return;
        }
        Platform.runLater(() -> {
            if (!isBattleMode) {
                showBattleMode();
            }
            skillsContainer.getChildren().clear();
            for (Skill skill : skills) {
                String btnText = String.format("%s\nDanno: %d MP: %d", skill.getName(), skill.getDamage(), skill.getCostMp());

                if (skill.getDamage()==0){
                    btnText = String.format("%s\nCura: %d MP: %d", skill.getName(), skill.getHeal(), skill.getCostMp());
                }
               Button btn = new Button(btnText);
                btn.setOnAction(e -> {
                    future.complete(skill);
                    disableButtons(skillsContainer);
                });
                skillsContainer.getChildren().add(btn);
            }
        });
    }

    /**
     * Aggiorna le barre di stato di giocatore e nemico.
     *
     * @param player Il personaggio del giocatore.
     * @param enemy  Il nemico corrente (può essere null).
     */
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {
        Platform.runLater(() -> {
            if (!isBattleMode) {
                showBattleMode();
            }
            playerNameLabel.setText(player.getName());
            playerHpBar.setProgress((double) player.getHp() / player.getMaxHp());
            playerHpLabel.setText(String.format("HP: %d/%d", player.getHp(), player.getMaxHp()));
            playerMpBar.setProgress((double) player.getMp() / player.getMaxMp());
            playerMpLabel.setText(String.format("MP: %d/%d", player.getMp(), player.getMaxMp()));
            if (enemy != null) {
                enemyNameLabel.setText(enemy.getName());
                enemyHpBar.setProgress((double) enemy.getHp() / enemy.getMaxHp());
                enemyHpLabel.setText(String.format("HP: %d/%d", enemy.getHp(), enemy.getMaxHp()));
            }
        });
    }

    /**
     * Mostra la notifica del turno del nemico con una breve animazione.
     *
     * @param enemyName Il nome del nemico.
     * @param damage    Il danno inflitto.
     */
    public void enemyTurnNotification(String enemyName, int damage) {
        Platform.runLater(() -> {
            enemyNameLabel.setText(enemyName + " attacca!");
            enemySprite.setOpacity(0.5);
            PauseTransition pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(e -> enemySprite.setOpacity(1.0));
            pause.play();
        });
    }

    /**
     * Gestisce il risultato della battaglia mostrando un alert e tornando al dialogo.
     *
     * @param playerWon {@code true} se il giocatore ha vinto.
     */
    public void battleResult(boolean playerWon) {
        String message = playerWon ? "Hai vinto!" : "Sei stato sconfitto...";
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Risultato combattimento");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        showDialogueMode();
        appendDialogueText(message);
    }

    /**
     * Imposta l'immagine dello speaker.
     *
     * @param speakerName Il nome dello speaker.
     */
    public void setSpeakerImage(String speakerName) {
        if (speakerName == null || speakerName.trim().isEmpty()) {
            return;
        }
        String imageName = speakerName.trim().toLowerCase().replace(" ", "_") + ".png";
        applyImage(speakerImage, IMAGES_DIR + imageName, true);
    }

    /**
     * Imposta l'immagine del nemico.
     *
     * @param imagePath Il percorso dell'immagine.
     */
    public void setEnemyImage(String imagePath) {
        applyImage(enemySprite, imagePath, false);
    }

    /**
     * Gestisce il salvataggio manuale della partita.
     */
    @FXML
    private void handleSaveGame() {
        if (gameEngine != null) {
            gameEngine.saveGame();
            appendDialogueText(GAME_SAVED_MESSAGE);
        } else {
            appendDialogueText(NO_GAME_MESSAGE);
        }
    }

  //metodi helper
    /**
     * Metodo generico per mostrare o nascondere un pannello
     * aggiornando correttamente le proprietà managed e visible.
     */
    private void toggleMode(Pane pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    /**
     * Crea un oggetto Text per il prompt delle opzioni con lo stile CSS corretto.
     */
    private Text createPromptText(String prompt) {
        Text promptText = new Text(prompt);
        promptText.getStyleClass().add("options-title");
        return promptText;
    }

    /**
     * Disabilita tutti i bottoni all'interno di un contenitore.
     */
    private void disableButtons(Pane container) {
        container.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        });
    }

    /**
     * Metodo generico per caricare un'immagine su un ImageView.
     * Se l'immagine non viene trovata o è corrotta, applica un fallback (se richiesto).
     *
     * @param target          L'ImageView su cui impostare l'immagine.
     * @param path            Il percorso dell'immagine.
     * @param useFallback     Se {@code true}, usa l'avatar di default in caso di errore.
     */
    private void applyImage(ImageView target, String path, boolean useFallback) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            if (!img.isError()) {
                target.setImage(img);
                target.setVisible(true);
            } else if (useFallback) {
                Image fallback = new Image(getClass().getResourceAsStream(AVATAR_IMAGE));
                target.setImage(fallback);
                target.setVisible(true);
            } else {
                System.err.println("Immagine non trovata: " + path);
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
            if (useFallback) {
                target.setVisible(false);
            }
        }
    }
}