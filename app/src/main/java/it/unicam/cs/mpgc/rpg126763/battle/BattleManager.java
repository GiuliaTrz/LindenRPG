package it.unicam.cs.mpgc.rpg126763.battle;

import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.Skill;
import it.unicam.cs.mpgc.rpg126763.persistence.GameState;
import it.unicam.cs.mpgc.rpg126763.ui.GameUI;

import java.util.concurrent.CompletableFuture;

/**
 * Gestisce il combattimento a turni tra il personaggio del giocatore e un nemico.
 * La classe si occupa di eseguire i turni del giocatore e del nemico, applicare gli effetti
 * delle skill, verificare le condizioni di vittoria o sconfitta e coordinare l'interazione
 * con l'interfaccia utente tramite {@link GameUI}.
 *
 * <p>Il flusso di combattimento è asincrono e utilizza {@link CompletableFuture} per
 * non bloccare il thread dell'interfaccia utente durante l'attesa delle scelte del giocatore
 * e per gestire il ritardo del turno nemico.</p>
 */
public class BattleManager {

    private final GameUI ui;

    /**
     * Crea un nuovo gestore di battaglie.
     *
     * @param ui l'interfaccia utente da utilizzare per mostrare messaggi e ricevere input.
     */
    public BattleManager(GameUI ui) {
        this.ui = ui;
    }

    /**
     * Avvia una battaglia utilizzando lo stato di gioco fornito.
     * Il metodo recupera il giocatore e il nemico dal {@link GameState} e delega
     * l'esecuzione del primo turno al metodo {@link #executeTurn(Personaggio, Nemico)}.
     *
     * @param gameState lo stato corrente del gioco, contenente il giocatore e il nemico.
     * @return un {@link CompletableFuture} che termina quando la battaglia è conclusa.
     */
    public CompletableFuture<Void> startBattle(GameState gameState) {
        return executeTurn(gameState.getPlayer(), gameState.getEnemy());
    }

    /**
     * Esegue ricorsivamente un turno di combattimento.
     * Se il giocatore o il nemico sono già stati sconfitti, comunica l'esito e termina,
     * altrimenti mostra lo stato aggiornato, chiede al giocatore di scegliere una skill,
     * applica gli effetti della skill, fa attaccare il nemico dopo un breve ritardo
     * e richiama se stesso per il turno successivo, se la battaglia non è terminata.
     *
     * @param player il personaggio del giocatore.
     * @param enemy  il nemico corrente.
     * @return un {@link CompletableFuture} che si completa quando la battaglia è terminata.
     */
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

            return CompletableFuture
                    .runAsync(
                            () -> {},
                            //aggiungo un piccolo delay per fare in modo che la ui abbia il tempo di aggiornarsi
                            CompletableFuture.delayedExecutor(800, java.util.concurrent.TimeUnit.MILLISECONDS)
                    )
                    .thenRun(() -> {
                        int damage = enemy.getAttack();
                        player.takeDamage(damage);
                        ui.enemyTurnNotification(enemy, damage);
                    })
                    .thenCompose(v -> {
                        if (!player.isAlive()) {
                            ui.battleResult(false);
                            return CompletableFuture.completedFuture(null);
                        }

                        return executeTurn(player, enemy);
                    });
        });
    }

    /**
     * Applica una skill scelta dal giocatore al nemico o al giocatore stesso.
     * Controlla che il giocatore abbia abbastanza MP, consuma i MP necessari,
     * infligge danno al nemico se la skill lo prevede, cura il giocatore se previsto
     * e aggiorna lo stato mostrato nell'interfaccia.
     *
     * @param player il personaggio del giocatore che usa la skill.
     * @param enemy  il nemico bersaglio.
     * @param skill  la skill da utilizzare.
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
            ui.showMessage("Hai recuperato" + skill.getHeal() + " HP!");
        }
        ui.updateBattleStatus(player, enemy);
    }
}