package com.tidal.pawpal.test;


import org.springframework.boot.CommandLineRunner;
import com.tidal.pawpal.models.Amministratore;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.CustomUserDetailsService.SecuredUser;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;

@Component
public class StartupTestRunner implements CommandLineRunner {

    private final SpecieServiceContract specieService;


    // private final ClienteServiceContract clienteService;


    // // Iniezione tramite Costruttore
    // public StartupTestRunner(ClienteServiceContract clienteService) {
    //     this.clienteService = clienteService;
    // }
    public StartupTestRunner(SpecieServiceContract specieService) {
        this.specieService = specieService;
    }

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
           System.out.println("la nuova specie è : "+specieService.cercaPerNomeSpecie("CANE")); 
            
            System.out.println("--- Esecuzione Codice Test Protetto Terminata con Successo ---");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5. Rimuovi il contesto di sicurezza (buona pratica!)
            SecurityContextHolder.clearContext();
        }
        // --- FINE CONTESTO SICURO SIMULATO ---
    }
}