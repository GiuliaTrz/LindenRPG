package it.unicam.cs.mpgc.rpg126763.battle;

import it.unicam.cs.mpgc.rpg126763.character.*;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.concurrent.CompletableFuture;

/**
 * Gestisce un combattimento a turni tra {@link Personaggio} e {@link Nemico}.
 * Versione per console: il combattimento è eseguito in modo sincrono,
 * ma il metodo startBattle restituisce un CompletableFuture già completato
 * per integrarsi con il flusso asincrono della GameEngine.
 * Quando si passerà a JavaFX, bisognerà rendere il metodo veramente asincrono.
 */
public class BattleManager {

    private final GameUI ui;

    public BattleManager(GameUI ui) {
        this.ui = ui;
    }

    /**
     * Avvia il combattimento e lo esegue **sincronicamente**.
     * Il metodo ritorna un {@link CompletableFuture} già completato
     * per adattarsi alle catene di thenCompose/thenRun.
     *
     * @param player personaggio giocante
     * @param enemy  nemico da affrontare
     * @return un CompletableFuture<Void> completato quando la battaglia termina
     */
    public CompletableFuture<Void> startBattle(Personaggio player, Nemico enemy) {
        // Esegui il combattimento sul thread corrente (non‑daemon)
        while (player.isAlive() && enemy.isAlive()) {
            ui.updateBattleStatus(player, enemy);

            // Attende la scelta della skill (blocca il thread corrente)
            Skill skill = ui.chooseSkill(player).join();

            useSkill(player, enemy, skill);

            if (!enemy.isAlive()) {
                break;
            }

            // Turno del nemico
            int damage = enemy.getAttack();
            player.takeDamage(damage);
            ui.enemyTurnNotification(enemy, damage);
        }

        // La battaglia è terminata
        ui.battleResult(player.isAlive());

        // Restituisce un future già completato
        return CompletableFuture.completedFuture(null);
    }

    /**
     * Applica l'effetto di una skill scelta dal giocatore.
     */
    private void useSkill(Personaggio player, Nemico enemy, Skill skill) {
        if (player.getMp() < skill.getCostMp()) {
            ui.showMessage("MP insufficienti!");
            return;
        }

        player.consumeMp(skill.getCostMp());

        if (skill.getDamage() > 0) {
            enemy.takeDamage(skill.getDamage());
            ui.showMessage("Usi " + skill.getName() + " e infliggi " + skill.getDamage() + " di danno");
        }

        if (skill.getHeal() > 0) {
            player.heal(skill.getHeal());
            ui.showMessage("Ti curi con " + skill.getHeal() + " HP");
        }
    }
}