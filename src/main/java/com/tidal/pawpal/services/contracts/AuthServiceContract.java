package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;

// Façade
public abstract class AuthServiceContract {

    @PreAuthorize("permitAll")
    public abstract User validaCredenziali(String username, String password);

    @PreAuthorize("permitAll")
    public abstract Cliente registraCliente(Map<String, String> dati);

    @PreAuthorize("permitAll")
    public abstract Veterinario registraVeterinario(List<Long> listaIdSpecie, List<Long> listaIdPrestazioni, Map<String, String> dati);

}