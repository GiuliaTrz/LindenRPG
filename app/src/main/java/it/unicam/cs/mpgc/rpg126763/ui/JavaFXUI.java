package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.CombatCharacter;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.Skill;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JavaFXUI implements GameUI {

    private GameWindowController controller;

    public void setController(GameWindowController controller) {
        this.controller = controller;
    }

    @Override
    public void showMessage(String message) {
        if (controller != null) {
            controller.appendDialogueText(message);
        } else {
            System.out.println(message);
        }
    }

    @Override
    public CompletableFuture<Integer> choose(String prompt, List<String> options) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        if (controller != null) {
            controller.showOptions(prompt, options, future);
        } else {
            future.complete(0);
        }
        return future;
    }

    @Override
    public void setEnemyImage(String imagePath) {
    controller.setEnemyImage(imagePath);
    }

    @Override
    public void setSpeakerImage(String speakerName) {
        controller.setSpeakerImage(speakerName);
    }

    @Override
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {
        if (controller != null) {
            controller.updateBattleStatus(player, enemy);
        }
    }

    @Override
    public CompletableFuture<Skill> chooseSkill(Personaggio player) {
        CompletableFuture<Skill> future = new CompletableFuture<>();
        if (controller != null) {
            controller.showSkillChoice(player, future);
        } else {
            future.complete(null);
        }
        return future;
    }

    @Override
    public void enemyTurnNotification(Nemico enemy, int damage) {
        if (controller != null) {
            controller.enemyTurnNotification(enemy.getName(), damage);
        } else {
            System.out.println(enemy.getName() + " infligge " + damage + " danni!");
        }
    }

    @Override
    public void battleResult(boolean playerWon) {
        if (controller != null) {
            controller.battleResult(playerWon);
        } else {
            System.out.println(playerWon ? "Hai vinto!" : "Sei stato sconfitto...");
        }
    }

    @Override
    public void onGameStarted() {}
}