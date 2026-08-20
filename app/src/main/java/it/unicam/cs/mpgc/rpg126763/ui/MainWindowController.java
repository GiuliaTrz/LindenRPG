package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.CombatCharacter;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainWindowController implements GameUI {

    @Override
    public void showMessage(String message) {

    }

    @Override
    public CompletableFuture<Integer> choose(String prompt, List<String> options) {
        return null;
    }

    @Override
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {

    }

    @Override
    public CompletableFuture<Skill> chooseSkill(Personaggio player) {
        return null;
    }

    @Override
    public void enemyTurnNotification(Nemico enemy, int damage) {

    }

    @Override
    public void battleResult(boolean playerWon) {

    }

    public void handleLoadGame(ActionEvent actionEvent) {
        System.out.println("load!");
    }

    public void handleNewGame(ActionEvent actionEvent) {
        System.out.println("new game!");
    }
}
