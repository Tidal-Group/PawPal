package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.exceptions.InvalidCredentialsException;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.CustomUserDetailsService.SecuredUser;
import com.tidal.pawpal.services.contracts.AuthServiceContract;

// Façade
@Service
public class AuthService extends AuthServiceContract {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VeterinarioService veterinarioService;

    @Override
    public User validaCredenziali(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
            return userService.cercaPerId(securedUser.getId());
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public Cliente registraCliente(Map<String, String> dati) {
        return clienteService.registra(dati);
    }

    @Override
    public Veterinario registraVeterinario(List<Long> listaIdSpecie, List<Long> listaIdPrestazioni, Map<String, String> dati) {
        return veterinarioService.registra(listaIdSpecie, listaIdPrestazioni, dati);
    }
    
}
