package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.*;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementazione testuale di GameUI.
 * Usa System.out per mostrare messaggi e Scanner per leggere input.
 */
public class ConsoleUI implements GameUI {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    @Override
    public CompletableFuture<Integer> choose(String prompt, List<String> options) {
        System.out.println(prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }
        System.out.print("Scelta > ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consuma newline
        return CompletableFuture.completedFuture(choice);
    }

    @Override
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {
        System.out.printf("HP: %d/%d MP: %d/%d | Nemico HP: %d/%d%n",
                player.getHp(), player.getMaxHp(), player.getMp(), player.getMaxMp(),
                enemy.getHp(), enemy.getMaxHp());
    }

    @Override
    public CompletableFuture<Skill> chooseSkill(Personaggio player) {
        List<Skill> skills = player.getSkills();
        List<String> names = skills.stream().map(Skill::getName).collect(Collectors.toList());
        int idx = choose("Scegli abilità:", names).join();  // bloccante per console
        return CompletableFuture.completedFuture(skills.get(idx));
    }

    @Override
    public void enemyTurnNotification(Nemico enemy, int damage) {
        System.out.println(enemy.getName() + " infligge " + damage + " danni.");
    }

    @Override
    public void battleResult(boolean playerWon) {
        System.out.println(playerWon ? "Hai vinto!" : "Sei stato sconfitto...");
    }
}