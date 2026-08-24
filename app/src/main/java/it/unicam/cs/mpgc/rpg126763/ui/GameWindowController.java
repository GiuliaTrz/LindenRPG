package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.CombatCharacter;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameWindowController {

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


    @FXML
    public void initialize() {
        showDialogueMode();
        optionsPanel.setVisible(false);
    }

    public void showDialogueMode() {
        isBattleMode = false;
        dialogueMode.setVisible(true);
        dialogueMode.setManaged(true);
        battleMode.setVisible(false);
        battleMode.setManaged(false);
        optionsPanel.setVisible(false);
    }

    public void showBattleMode() {
        isBattleMode = true;
        dialogueMode.setVisible(false);
        dialogueMode.setManaged(false);
        battleMode.setVisible(true);
        battleMode.setManaged(true);
        optionsPanel.setVisible(false);
    }

    public void setJavaFXUI(JavaFXUI ui) {
        this.ui = ui;
        ui.setController(this);
    }
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
    public void appendDialogueText(String text, String speaker) {
        Platform.runLater(() -> {
            if (isBattleMode) {
                showDialogueMode();
            }
            if (speaker != null && !speaker.isBlank()) {
                speakerText.setText(speaker);
            } else {
                speakerText.setText("");
            }
            dialogueTextArea.appendText(text + "\n\n");
            historyBuilder.append(speaker != null ? speaker + ": " : "");
            historyBuilder.append(text).append("\n");
        });
    }

    public void appendDialogueText(String text) {
        appendDialogueText(text, null);
    }

    public void showOptions(String prompt, List<String> options, CompletableFuture<Integer> future) {
        Platform.runLater(() -> {
            if (isBattleMode) {
                showDialogueMode();
            }
            optionsPanel.getChildren().clear();
            Text promptText = new Text(prompt);
            optionsPanel.getChildren().add(promptText);
            for (int i = 0; i < options.size(); i++) {
                final int index = i;
                Button btn = new Button(options.get(i));
                btn.setOnAction(e -> {
                    future.complete(index);
                    optionsPanel.getChildren().forEach(node -> {
                        if (node instanceof Button) {
                            ((Button) node).setDisable(true);
                        }
                    });
                });
                optionsPanel.getChildren().add(btn);
            }
            optionsPanel.setVisible(true);
        });
    }

    public void setSpeakerImage(String speakerName) {
        if (speakerName == null || speakerName.trim().isEmpty()) {
            return;
        }
        String imageName = speakerName.trim().toLowerCase().replace(" ", "_") + ".png";
        setImageFromResource("/it/unicam/cs/mpgc/rpg126763/images/" + imageName);
    }

    private void setImageFromResource(String path) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            if (!img.isError()) {
                speakerImage.setImage(img);
                speakerImage.setVisible(true);
            } else {
                Image fallback = new Image(getClass().getResourceAsStream(
                        "/it/unicam/cs/mpgc/rpg126763/images/avatar.png"));
                speakerImage.setImage(fallback);
                speakerImage.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Immagine speaker non trovata: " + path);
            speakerImage.setVisible(false);
        }
    }

    public void setEnemyImage(String imagePath) {
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            if (!img.isError()) {
                enemySprite.setImage(img);
            } else {
                System.err.println("Immagine non trovata: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }
    }

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
            System.out.println(player.getHp());
            System.out.println(enemy.getHp());
        });
    }

    public void showSkillChoice(Personaggio player, CompletableFuture<Skill> future) {
        List<Skill> skills = player.getSkills();
        if (skills.isEmpty()) {
            appendDialogueText("Non hai skill disponibili!");
            future.complete(null);
            return;
        }
        Platform.runLater(() -> {
            if (!isBattleMode) {
                showBattleMode();
            }
            skillsContainer.getChildren().clear();
            for (Skill skill : skills) {
                Button btn = new Button(String.format("%s\nDanno: %d MP: %d",
                        skill.getName(), skill.getDamage(), skill.getCostMp()));
                btn.setOnAction(e -> {
                    future.complete(skill);
                    skillsContainer.getChildren().forEach(node -> {
                        if (node instanceof Button) {
                            ((Button) node).setDisable(true);
                        }
                    });
                });
                skillsContainer.getChildren().add(btn);
            }
        });
    }

    public void enemyTurnNotification(String enemyName, int damage) {
        Platform.runLater(() -> {
            enemyNameLabel.setText(enemyName + " attacca!");
            enemySprite.setOpacity(0.5);
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
                Platform.runLater(() -> enemySprite.setOpacity(1.0));
            }).start();
        });
    }

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

    @FXML
    private void handleSaveGame() {
        if (gameEngine != null) {
            gameEngine.saveGame();
            appendDialogueText("Partita salvata!");
        } else {
            appendDialogueText("Nessuna partita in corso!");
        }
    }
}