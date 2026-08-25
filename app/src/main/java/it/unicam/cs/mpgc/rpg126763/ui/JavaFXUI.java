package it.unicam.cs.mpgc.rpg126763.ui;

import it.unicam.cs.mpgc.rpg126763.character.CombatCharacter;
import it.unicam.cs.mpgc.rpg126763.character.Nemico;
import it.unicam.cs.mpgc.rpg126763.character.Personaggio;
import it.unicam.cs.mpgc.rpg126763.models.Skill;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementazione dell'interfaccia {@link GameUI} che fa da ponte tra il motore
 * di gioco e l'interfaccia grafica JavaFX delegando le operazioni
 * a un {@link GameWindowController}.
 *
 * Se il controller non è ancora stato impostato, i metodi gestiscono
 * l'assenza della GUI in modo sicuro (stampando a console o completando
 * la future con un valore di default) per evitare NullPointerException.
 */
public class JavaFXUI implements GameUI {

    private GameWindowController controller;

    /**
     * Imposta il controller grafico a cui delegare le operazioni.
     *
     * @param controller il controller della finestra di gioco.
     */
    public void setController(GameWindowController controller) {
        this.controller = controller;
    }

    /**
     * Verifica se il controller grafico è stato correttamente inizializzato.
     *
     * @return {@code true} se il controller è presente, {@code false} altrimenti.
     */
    private boolean hasController() {
        return controller != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(String message) {
        if (hasController()) {
            controller.appendDialogueText(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Integer> choose(String prompt, List<String> options) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        if (hasController()) {
            controller.showOptions(prompt, options, future);
        } else {
            future.complete(0);
        }
        return future;
    }

    /**
     * {@inheritDoc}
     * Se il controller non è presente, il metodo non esegue alcuna operazione.
     */
    @Override
    public void setEnemyImage(String imagePath) {
        if (hasController()) {
            controller.setEnemyImage(imagePath);
        }
    }

    /**
     * {@inheritDoc}
     * Se il controller non è presente, il metodo non esegue alcuna operazione.
     */
    @Override
    public void setSpeakerImage(String speakerName) {
        if (hasController()) {
            controller.setSpeakerImage(speakerName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBattleStatus(CombatCharacter player, CombatCharacter enemy) {
        if (hasController()) {
            controller.updateBattleStatus(player, enemy);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Skill> chooseSkill(Personaggio player) {
        CompletableFuture<Skill> future = new CompletableFuture<>();
        if (hasController()) {
            controller.showSkillChoice(player, future);
        } else {
            future.complete(null);
        }
        return future;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enemyTurnNotification(Nemico enemy, int damage) {
        if (hasController()) {
            controller.enemyTurnNotification(enemy.getName(), damage);
        } else {
            System.out.println(enemy.getName() + " infligge " + damage + " danni!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void battleResult(boolean playerWon) {
        if (hasController()) {
            controller.battleResult(playerWon);
        } else {
            System.out.println(playerWon ? "Hai vinto!" : "Sei stato sconfitto...");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameStarted() {
    }
}