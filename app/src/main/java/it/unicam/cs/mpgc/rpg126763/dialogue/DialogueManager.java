package it.unicam.cs.mpgc.rpg126763.dialogue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg126763.models.MageType;
import it.unicam.cs.mpgc.rpg126763.models.NPC;
import it.unicam.cs.mpgc.rpg126763.models.Personaggio;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DialogueManager {

    private Map<String, Dialogue> dialogues;
    private NPC npc;

    public DialogueManager(String filePath, NPC npc) {
        this.npc = npc;
        load();
    }

    private void load() {

        try {
            Gson gson = new Gson();

            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("dialogues.json");

            if (inputStream == null) {
                throw new RuntimeException("file dialogues.json non trovato");
            }

            InputStreamReader reader = new InputStreamReader(inputStream);

            Type type = new TypeToken<List<Dialogue>>() {}.getType();

            List<Dialogue> list = gson.fromJson(reader, type);

            dialogues = list.stream()
                    .collect(Collectors.toMap(Dialogue::getId, d -> d));

            System.out.println("Dialoghi caricati: " + dialogues.size());//test

        } catch (Exception e) {
            System.out.println("Errore nel caricamento dei dialoghi:");
            e.printStackTrace();
        }
    }

    //start dialogo
    public void start(String startId, Personaggio player) {

        if (dialogues == null || dialogues.isEmpty()) {
            System.out.println("Nessun dialogo caricato");
            return;
        }
//sistema di id per dialoghi?
        Scanner scanner = new Scanner(System.in);

        Dialogue current = dialogues.get(startId);

        while (current != null) {

            System.out.println("\n" + this.npc.getName() + ": " + current.getText());

            if (!current.hasOptions()) {
                break;
            }

            for (int i = 0; i < current.getOptions().size(); i++) {
                System.out.println((i + 1) + ") " + current.getOptions().get(i).getText());
            }

            int choice = scanner.nextInt() - 1;

            if (choice < 0 || choice >= current.getOptions().size()) {
                System.out.println("Scelta non valida, riprova.");
                continue;
            }

            Option selected = current.getOptions().get(choice);

            handleEffect(selected.getEffect(), player);

            current = dialogues.get(selected.getNextDialogueId());
        }
    }

    //effetti per magie --> potrebbe essere un enum
    private void handleEffect(String effect, Personaggio player) {

        if (effect == null) return;

        switch (effect) {

            case "SET_MAGE_EMBER":
                player.setMageType(MageType.EMBERWEAVER);
                System.out.println("Una fiamma si accende dentro di te...");
                //non va bene metterli nel dialogue manager, non è più configurabile
                break;
                //invece di questo, ho enum per istruzione SET (enumSET.mageember..)

            case "SET_MAGE_TIDE":
                player.setMageType(MageType.TIDEWEAVER);
                System.out.println("Senti il fluire dell'acqua...");
                break;

            case "SET_MAGE_GUST":
                player.setMageType(MageType.GUSTWEAVER);
                System.out.println("Il vento risponde al tuo respiro...");
                break;

            case "SET_MAGE_EARTH":
                player.setMageType(MageType.EARTHWEAVER);
                System.out.println("Le radici ti ancorano alla terra...");
                break;
        }
    }
}