package it.unicam.cs.mpgc.rpg126763;

import it.unicam.cs.mpgc.rpg126763.battle.BattleManager;
import it.unicam.cs.mpgc.rpg126763.dialogue.DialogueManager;
import it.unicam.cs.mpgc.rpg126763.models.*;
import it.unicam.cs.mpgc.rpg126763.persistence.SaveManager;

import java.util.Scanner;

public class GameApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SaveManager saveManager = new SaveManager();

        System.out.println("LindenRPG");
        System.out.println("1) New story");
        System.out.println("2) Load");
        System.out.print("> ");

        int choice = scanner.nextInt();

        Personaggio player;

        if (choice == 2) {
            player = saveManager.load("savegame.json");

            if (player == null) {
                System.out.println("Nessun salvataggio trovato. Nuova partita...");
                player = new Personaggio("Linden");
            }

        } else {
            player = new Personaggio("Linden");
        }

        System.out.println("\nStart");
        System.out.println(player);
        DialogueManager dm = new DialogueManager("src/main/resources/dialogues.json");
        dm.start("start", player);
        Nemico slime = new Nemico("Slime", 60, 8);
        BattleManager battle = new BattleManager();
        battle.startBattle(player, slime);
        saveManager.save("savegame.json", player);

        System.out.println("\nFine demo!");
    }
}