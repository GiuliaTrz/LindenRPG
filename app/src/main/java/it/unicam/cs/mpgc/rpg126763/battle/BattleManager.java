package it.unicam.cs.mpgc.rpg126763.battle;

import it.unicam.cs.mpgc.rpg126763.models.*;
import it.unicam.cs.mpgc.rpg126763.character.*;

import java.util.List;
import java.util.Scanner;

public class BattleManager {

    private final Scanner scanner = new Scanner(System.in);

    public void startBattle(Personaggio player, Nemico enemy) {

        System.out.println("\n Inizia il combattimento contro " + enemy.getName() + "!\n");

        while (player.isAlive() && enemy.isAlive()) {

            playerTurn(player, enemy);

            if (!enemy.isAlive()) {
                System.out.println("\n Complimenti! Hai sconfitto " + enemy.getName() + "!");
                break;
            }

            enemyTurn(player, enemy);

            if (!player.isAlive()) {
                System.out.println("\n Oh no...");
            }
        }
    }

    private void playerTurn(Personaggio player, Nemico enemy) {

        System.out.println("\nE' il tuo turno!");
        System.out.println("HP Player: " + player.getHp() + " | MP: " + player.getMp());
        System.out.println("HP Enemy: " + enemy.getHp());

        List<Skill> skills = player.getSkills();

        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + ") " + skills.get(i).getName());
        }

        System.out.print("Scegli skill: ");
        int choice = scanner.nextInt() - 1;

        Skill skill = skills.get(choice);

        useSkill(player, enemy, skill);
    }

    private void enemyTurn(Personaggio player, Nemico enemy) {

        System.out.println("\nE' il turno del nemico!");

        int damage = enemy.getAttack();

        player.takeDamage(damage);

        System.out.println(enemy.getName() + " hai ricevuto" + damage + " danni!");
    }

    private void useSkill(Personaggio player, Nemico enemy, Skill skill) {

        if (player.getMp() < skill.getCostMp()) {
            System.out.println("Non hai abbastanza MP!");
            return;
        }

        player.consumeMp(skill.getCostMp());

        if (skill.getDamage() > 0) {
            enemy.takeDamage(skill.getDamage());
            System.out.println("Hai usato " + skill.getName() +
                    " e inflitto " + skill.getDamage() + " danni!");
        }

        if (skill.getHeal() > 0) {
            player.heal(skill.getHeal());
            System.out.println("Ricevi " + skill.getHeal() + " HP!");
        }
    }
}