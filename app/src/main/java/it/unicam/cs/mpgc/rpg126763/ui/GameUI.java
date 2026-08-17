package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.*;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaccia per l'interazione con l'utente.
 * Tutti i metodi di input restituiscono CompletableFuture per supportare UI asincrone.
 */
public interface GameUI {
    void showMessage(String message);
    CompletableFuture<Integer> choose(String prompt, List<String> options);
    void updateBattleStatus(CombatCharacter player, CombatCharacter enemy);
    CompletableFuture<Skill> chooseSkill(Personaggio player);
    void enemyTurnNotification(Nemico enemy, int damage);
    void battleResult(boolean playerWon);
}