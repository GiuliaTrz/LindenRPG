# LindenRPG

LindenRPG è un gioco di ruolo (in versione demo) sviluppato in Java con interfaccia grafica JavaFX.  
Il giocatore veste i panni di un giovane mago che, dopo un misterioso risveglio nella foresta, dove incontra una creatura magica, deve scegliere il proprio elemento magico e affrontare nemici misteriosi.
Il progetto è stato realizzato per il corso di Modellazione e Gestione della Conoscenza (AA 2025/26).

## Documentazione

La documentazione completa del progetto è disponibile nella [Wiki](https://github.com/GiuliaTrz/LindenRPG/wiki).

## Dichiarazione dettagliata di utilizzo di strumenti di AI

Sono stati utilizzati strumenti di intelligenza artificiale generativa, in particolare ChatGPT (modello conversazionale), per i seguenti scopi:

1) Progettazione dell’architettura: suggerimenti per favorire l’estendibilità.
2) Scrittura di codice: boilerplate CSS, correzioni per la parte grafica, porzioni di logica per la gestione asincrona (CompletableFuture).
3) Refactoring: proposte di miglioramento del codice per aderire ai principi SOLID e ridurre duplicazioni.
4) Debugging: supporto nell’identificazione di bug.
5) Generazione immagini: creazione di asset grafici e sprite.
6) Documentazione: assistenza nella stesura di README e Wiki.
 

Nessuna porzione di codice è stata copiata senza comprenderne il funzionamento.

## Requisiti

- Java 25 LTS
- JavaFX 26
- Gradle 9.4 (incluso nel wrapper)

## Compilazione ed esecuzione

Da terminale, nella directory principale del progetto:

```bash
./gradlew build
./gradlew run



