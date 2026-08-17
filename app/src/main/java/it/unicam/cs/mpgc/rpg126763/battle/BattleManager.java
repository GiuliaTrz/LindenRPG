package it.unicam.cs.mpgc.rpg126763.battle;

import it.unicam.cs.mpgc.rpg126763.character.*;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.concurrent.CompletableFuture;

/**
 * Gestisce un combattimento a turni tra {@link Personaggio} e {@link Nemico}.
 * L'esecuzione è asincrona per non bloccare l'interfaccia utente.
 *
 * Il flusso è realizzato tramite {@link CompletableFuture} e {@code runAsync}
 * per evitare una ricorsione profonda e mantenere il codice reattivo.
 */
public class BattleManager {
    private final GameUI ui;

    /**
     * Crea un BattleManager associato a una specifica interfaccia utente.
     * @param ui l'interfaccia utente (console, JavaFX, ...)
     */
    public BattleManager(GameUI ui) {
        this.ui = ui;
    }

    /**
     * Avvia il combattimento.
     *
     * @param player personaggio giocante
     * @param enemy  nemico da affrontare
     * @return un {@link CompletableFuture} che si completa quando la battaglia termina
     */
    public CompletableFuture<Void> startBattle(Personaggio player, Nemico enemy) {
        CompletableFuture<Void> battleEnd = new CompletableFuture<>();
        turnLoop(player, enemy, battleEnd);
        return battleEnd;
    }

    /**
     * Loop dei turni. Ogni turno viene eseguito in un nuovo task asincrono.
     */
    private void turnLoop(Personaggio player, Nemico enemy, CompletableFuture<Void> endFuture) {
        if (!player.isAlive() || !enemy.isAlive()) {
            ui.battleResult(player.isAlive());
            endFuture.complete(null);
            return;
        }

        ui.updateBattleStatus(player, enemy);

        ui.chooseSkill(player).thenCompose(skill -> {
            useSkill(player, enemy, skill);
            if (!enemy.isAlive()) {
                ui.battleResult(true);
                endFuture.complete(null);
                return CompletableFuture.<Void>completedFuture(null);
            }

            // Turno nemico
            int damage = enemy.getAttack();
            player.takeDamage(damage);
            ui.enemyTurnNotification(enemy, damage);

            if (!player.isAlive()) {
                ui.battleResult(false);
                endFuture.complete(null);
                return CompletableFuture.<Void>completedFuture(null);
            }

            // Prossimo turno (nuova esecuzione asincrona)
            return CompletableFuture.runAsync(() -> turnLoop(player, enemy, endFuture));
        });
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