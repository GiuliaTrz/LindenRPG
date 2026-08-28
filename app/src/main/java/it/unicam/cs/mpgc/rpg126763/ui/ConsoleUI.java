package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.*;
import it.unicam.cs.mpgc.rpg126763.models.Skill;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementazione testuale di {@link GameUI}, utile (e usato per) per il debugging o per eseguire
 * il gioco da terminale senza interfaccia grafica.
 *
 * <p>L'input da console è bloccante per sua natura: i metodi attendono
 *      l'inserimento dell'utente prima di restituire un risultato.</p>
 */
public class ConsoleUI implements GameUI {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Mostra un messaggio all'utente sulla console.
     *
     * @param message il messaggio da visualizzare
     */
    @Override
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Chiede all'utente di scegliere una tra le opzioni proposte e restituisce
     * l'indice selezionato tramite un {@link CompletableFuture}.
     *
     * @param prompt  il messaggio da mostrare prima dell'elenco delle opzioni
     * @param options la lista delle opzioni disponibili
     * @return un future contenente l'indice scelto (0‑based)
     */
    @Override
    public CompletableFuture<Integer> choose(String prompt, List<String> options) {
        System.out.println(prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }
        System.out.print("Scelta > ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consuma il newline rimasto nel buffer
        return CompletableFuture.completedFuture(choice);
    }

    /**
     * Metodo vuoto: la console non supporta la visualizzazione di immagini.
     *
     * @param imagePath il percorso dell'immagine (ignorato)
     */
    @Override
    public void setEnemyImage(String imagePath) {
        // nessuna operazione necessaria
    }

    /**
     * Metodo vuoto: la console non supporta la visualizzazione di immagini.
     *
     * @param speakerName il nome dello speaker (ignorato)
     */
    @Override
    public void setSpeakerImage(String speakerName) {
        // nessuna operazione necessaria
    }

    /**
     * Mostra lo stato attuale del combattimento: HP e MP del giocatore e HP del nemico.
     *
     * @param player il personaggio del giocatore
     * @param enemy  il nemico corrente
     */
    @Override
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {
        System.out.printf("HP: %d/%d MP: %d/%d | Nemico HP: %d/%d%n",
                player.getHp(), player.getMaxHp(), player.getMp(), player.getMaxMp(),
                enemy.getHp(), enemy.getMaxHp());
    }

    /**
     * Chiede al giocatore di scegliere una skill tra quelle disponibili.
     * Il metodo è bloccante perché utilizza {@link #choose(String, List)} e
     * attende la risposta dell'utente.
     *
     * @param player il personaggio del giocatore
     * @return un future contenente la skill selezionata
     */
    @Override
    public CompletableFuture<Skill> chooseSkill(Personaggio player) {
        List<Skill> skills = player.getSkills();
        List<String> names = skills.stream().map(Skill::getName).collect(Collectors.toList());
        int idx = choose("Scegli abilità:", names).join(); // bloccante per console
        return CompletableFuture.completedFuture(skills.get(idx));
    }

    /**
     * Mostra una notifica relativa al turno del nemico, indicando il danno inflitto.
     *
     * @param enemy  il nemico che ha attaccato
     * @param damage il danno inflitto
     */
    @Override
    public void enemyTurnNotification(Nemico enemy, int damage) {
        System.out.println(enemy.getName() + " infligge " + damage + " danni.");
    }

    /**
     * Mostra l'esito della battaglia.
     *
     * @param playerWon true se il giocatore ha vinto, false se è stato sconfitto
     */
    @Override
    public void battleResult(boolean playerWon) {
        System.out.println(playerWon ? "Hai vinto!" : "Oh no! Sei stato sconfitto...");
    }
}