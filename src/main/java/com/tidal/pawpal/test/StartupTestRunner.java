package com.tidal.pawpal.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tidal.pawpal.models.Amministratore;
import com.tidal.pawpal.services.CustomUserDetailsService.SecuredUser;
import com.tidal.pawpal.services.VeterinarioService;

@Component
public class StartupTestRunner implements CommandLineRunner {

    // private final ClienteServiceContract clienteService;
    // TESTARE
    @Autowired
    private VeterinarioService veterinarioService;
    // Iniezione tramite Costruttore
    // public StartupTestRunner(ClienteServiceContract clienteService) {
    //     this.clienteService = clienteService;
    // }
    // //COSTRUTTORE PER TESTARE

    @Override
    public void run(String... args) throws Exception {
        
        // --- INIZIO CONTESTO SICURO SIMULATO ---
        try {

            Amministratore admin = new Amministratore();
            admin.setRuolo("ADMIN");
            // 1. Crea un Principal fittizio (es. un Admin)
            // L'ID è 0 perché non stiamo cercando di accedere a dati specifici dell'utente loggato in questo contesto
            SecuredUser fakeAdmin = new SecuredUser(
                admin
            ); 

            // 2. Crea l'oggetto Authentication
            UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(fakeAdmin, null, fakeAdmin.getAuthorities());
            
            // 3. Imposta il contesto di sicurezza per il thread corrente
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            // 4. ESEGUI IL TUO CODICE PROTETTO QUI
            System.out.println("--- Esecuzione Codice Test Protetto Iniziata ---");


            // System.out.println("IL CLIENTE E' QUESTO: " + clienteService.cercaPerEmail("giovannifranco2000@gmail.com"));

            // System.out.println("IL CLIENTE E' QUESTO: " + clienteService.cercaPerTelefono("3334445555"));
            // List<Long> specie = new ArrayList<>();
            // List<Long> prestazioni = new ArrayList<>();
            // Map<String, String> data = new HashMap<>();
            // List<Long> specie = new ArrayList<>();
            // List<Long> prestazioni = new ArrayList<>();
            // specie.add(1L);
            // prestazioni.add(1L);
            // data.put("nome", "Dr. Test Veterinario");
            // data.put("cognome", "Rossi");
            // data.put("email", "dr.rssi@test.com");
            // data.put("username", "drrossi");
            // data.put("password", "password123");
            // data.put("specializzazione", "Cardiologia");
            // data.put("indirizzoStudio", "Via Roma 123, Milano");
            // System.out.println("IL VETERINARIO E' QUESTO: " + veterinarioService.registra(specie, prestazioni, data));
            // System.out.println("--- Esecuzione Codice Test Protetto Terminata con Successo ---");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5. Rimuovi il contesto di sicurezza (buona pratica!)
            SecurityContextHolder.clearContext();
        }
        // --- FINE CONTESTO SICURO SIMULATO ---
    }
}