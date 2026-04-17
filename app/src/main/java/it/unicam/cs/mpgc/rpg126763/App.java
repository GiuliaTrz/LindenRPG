package it.unicam.cs.mpgc.rpg126763;

//import it.unicam.cs.mpgc.rpg126763.dialogue.manager.DialogueManager;
//import it.unicam.cs.mpgc.rpg126763.engine.BattleManager;
import it.unicam.cs.mpgc.rpg126763.models.Item;
import it.unicam.cs.mpgc.rpg126763.models.ItemType;
import it.unicam.cs.mpgc.rpg126763.models.Nemico;
import it.unicam.cs.mpgc.rpg126763.models.Personaggio;

public class App {

    public static void main(String[] args) {

        //Creazione personaggio
        Personaggio player = new Personaggio("Linden");


        //Dialoghi iniziali (portano alla scelta  della classe)
        //  DialogueManager dm = new DialogueManager("dialogues.json");
        // dm.start("start", player);

        System.out.println("\nPersonaggio creato:");
        System.out.println(player);

        Item crystalHeal = new Item(
                "Cristallo blu",
                "Un cristallo dalle sfumature bluastre. Emana energia vitale.",
                ItemType.HEAL,
                30
        );

        Item crystalBuff = new Item(
                "Cristallo argenteo",
                "Aumenta la tua energia magica",
                ItemType.BUFF,
                20
        );

        Item crystalQuest = new Item(
                "Cristallo Antico",
                "Un oggetto misterioso",
                ItemType.QUEST,
                0
        );

        player.addItem(crystalHeal);
        player.addItem(crystalBuff);

        player.printInventory();

        player.useItem(crystalHeal);
        // primo combattimento
        // Nemico slime = new Nemico("Slime", 50, 8);

        // BattleManager bm = new BattleManager();
        // bm.startBattle(player, slime);

    }
}
