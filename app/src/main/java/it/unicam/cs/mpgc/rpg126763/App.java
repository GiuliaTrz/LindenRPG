package it.unicam.cs.mpgc.rpg126763;

import it.unicam.cs.mpgc.rpg126763.engine.GameEngine;
import it.unicam.cs.mpgc.rpg126763.ui.ConsoleUI;

import java.util.Scanner;

/**
 * Classe principale (temporanea, da fixare) per l'esecuzione testuale da terminale di LindenRPG.
 *
 * Mostra un menu per la scelta tra nuova partita e caricamento,
 * quindi delega l'esecuzione al {@link GameEngine} tramite la UI console.
 */
public class App {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(ui);

        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Nuova partita");
        System.out.println("2) Carica partita");
        System.out.print("Scelta > ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consuma newline

        if (choice == 2) {
            engine.loadGame();
        } else {
            System.out.print("Inserisci il nome del personaggio: ");
            String name = scanner.nextLine();
            engine.startNewGame(name.isBlank() ? "Linden" : name);
        }
    }
}