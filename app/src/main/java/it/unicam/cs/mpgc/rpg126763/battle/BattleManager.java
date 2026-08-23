package it.unicam.cs.mpgc.rpg126763.battle;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.concurrent.CompletableFuture;

public class BattleManager {

    private final GameUI ui;

    public BattleManager(GameUI ui) {
        this.ui = ui;
    }

    public CompletableFuture<Void> startBattle(Personaggio player, Nemico enemy) {
        return executeTurn(player, enemy);
    }

    private CompletableFuture<Void> executeTurn(Personaggio player, Nemico enemy) {
        if (!player.isAlive() || !enemy.isAlive()) {
            ui.battleResult(player.isAlive());
            return CompletableFuture.completedFuture(null);
        }

        ui.updateBattleStatus(player, enemy);

        return ui.chooseSkill(player).thenCompose(skill -> {
            if (skill != null) {
                useSkill(player, enemy, skill);
            }

            if (!enemy.isAlive()) {
                ui.battleResult(true);
                return CompletableFuture.completedFuture(null);
            }

            int damage = enemy.getAttack();
            player.takeDamage(damage);
            ui.enemyTurnNotification(enemy, damage);

            if (!player.isAlive()) {
                ui.battleResult(false);
                return CompletableFuture.completedFuture(null);
            }

            return executeTurn(player, enemy);
        });
    }

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
            ui.showMessage("Ti curi con " + skill.getHeal() + " HP/MP!");
        }
    }
}