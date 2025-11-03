package com.tidal.pawpal.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponents; // Importa questo
import org.springframework.web.util.UriComponentsBuilder; // Importa questo

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {

        // 1. Ottieni l'URL da cui proviene la richiesta di login
        String redirectUrl = request.getParameter("redirectUrl");

        // 2. Controlla i ruoli
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        UriComponentsBuilder uriBuilder;

        if (isAdmin) {
            // Se è ADMIN, ignora tutto e manda alla dashboard admin
            uriBuilder = UriComponentsBuilder.fromPath("/admin");

        } else {
            // Se è CLIENTE o VETERINARIO
            
            // Se non c'è un URL (es. login diretto), imposta la home come default
            if (redirectUrl == null || redirectUrl.isEmpty()) {
                redirectUrl = "/";
            }
            
            // 3. PARTE CHIAVE: Pulisci l'URL
            // Parsa l'URL da cui siamo venuti (es. /contatti?error)
            UriComponents components = UriComponentsBuilder.fromUriString(redirectUrl).build();
            
            // Ricostruisci l'URL da zero, preservando solo il path (es. /contatti)
            uriBuilder = UriComponentsBuilder.fromPath(components.getPath());

            // 4. Ricopia solo i parametri che NON sono "modal", "error" o "loginSuccess"
            // Questo è ciò che fa sparire il messaggio di errore dopo il login corretto
            components.getQueryParams().forEach((key, values) -> {
                if (!key.equalsIgnoreCase("modal") && 
                    !key.equalsIgnoreCase("error") && 
                    !key.equalsIgnoreCase("loginSuccess")) 
                {
                    uriBuilder.queryParam(key, values.toArray());
                }
            });
        }

        // 5. Aggiungi il parametro di successo IN OGNI CASO
        uriBuilder.queryParam("loginSuccess", "true");

        // 6. Esegui il reindirizzamento finale
        response.sendRedirect(uriBuilder.build().toUriString());
    }
    
}