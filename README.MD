# 🐾 PawPal - Versione 1.0

PawPal è una piattaforma web completa (Versione 1.0) progettata per connettere i proprietari di animali domestici con veterinari professionisti in modo semplice ed efficiente. L'applicazione facilita la ricerca di specialisti, la prenotazione di appuntamenti e la gestione delle recensioni, fornendo dashboard dedicate sia per i clienti che per i veterinari.

---

## 🚀 Funzionalità Principali

Questa versione include le seguenti funzionalità:

* **Autenticazione Sicura:** Sistema di registrazione e login basato sui ruoli (`ROLE_CLIENTE`, `ROLE_VETERINARIO`) gestito con Spring Security.
* **Ricerca Veterinari:** Un motore di ricerca pubblico per filtrare i veterinari per nome, cognome, città, specie trattata o prestazione offerta.
* **Profili Veterinari:** Una vista modale (popup) che mostra i dettagli di un veterinario, incluse le sue specializzazioni, l'indirizzo e le recensioni ricevute.
* **Gestione Appuntamenti:** I clienti possono prenotare appuntamenti con i veterinari direttamente dai loro profili.
* **Sistema di Recensioni:** I clienti possono lasciare un voto (da 1 a 5) e un commento per i veterinari dopo una visita.
* **Dashboard Cliente:**
    * **Panoramica:** Una vista di benvenuto con il prossimo appuntamento e statistiche rapide (totale appuntamenti, totale recensioni).
    * **Modifica Dati:** Aggiorna le informazioni del profilo.
    * **Appuntamenti:** Visualizza lo storico e gli appuntamenti futuri.
    * **Recensioni:** Visualizza tutte le recensioni scritte.
* **Dashboard Veterinario:**
    * **Panoramica:** Mostra appuntamenti di oggi, voto medio e statistiche.
    * **Modifica Dati:** Aggiorna il profilo professionale (specializzazione, indirizzo, ecc.).
    * **Appuntamenti:** Visualizza tutti gli appuntamenti ricevuti.
    * **Recensioni:** Visualizza tutte le recensioni ricevute.

---

## 🛠️ Tecnologie Utilizzate

* **Backend:**
    * Java 17
    * Spring Boot 3
    * Spring Web (per API REST e controller MVC)
    * Spring Security (per l'autenticazione e autorizzazione)
    * Spring Data JPA (per l'interazione con il database)
    * Lombok
* **Frontend:**
    * Thymeleaf (come motore di templating)
    * Bootstrap 5 (per il layout e i componenti UI)
    * JavaScript (per la gestione dinamica delle viste della dashboard e delle modali)
* **Database:**
    * MySQL (configurato in `application.properties`)
* **Build:**
    * Apache Maven

---

## 🏁 Come Avviare il Progetto

Per eseguire questo progetto in locale, segui questi passaggi:

### 1. Prerequisiti

* Java JDK 17 o superiore
* Apache Maven
* Un server MySQL in esecuzione (es. XAMPP, MAMP, o un'istanza Docker)

### 2. Configurazione del Database

1.  Accedi al tuo server MySQL.
2.  Crea un nuovo database chiamato `pawpal` (o il nome che preferisci, ma assicurati di aggiornarlo nel file di configurazione).

    ```sql
    CREATE DATABASE pawpal;
    ```

### 3. Configurazione dell'Applicazione

1.  Apri il file: `src/main/resources/application.properties`.
2.  Modifica le seguenti righe per farle corrispondere alla tua configurazione MySQL locale (principalmente il tuo username e password):

    ```properties
    # URL del database
    spring.datasource.url=jdbc:mysql://localhost:3306/pawpal

    # Inserisci il tuo username e password di MySQL
    spring.datasource.username=root
    spring.datasource.password=tua_password
    
    # Questa riga crea/aggiorna automaticamente le tabelle all'avvio
    spring.jpa.hibernate.ddl-auto=update
    ```

### 4. Esecuzione

1.  Apri un terminale nella cartella principale del progetto (`PROJECT-WORK/PawPal`).
2.  Esegui il progetto utilizzando Maven:

    ```bash
    mvn spring-boot:run
    ```

3.  L'applicazione sarà disponibile all'indirizzo `http://localhost:8080`.

---
message.txt
