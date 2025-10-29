package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

import jakarta.transaction.Transactional;

public abstract class UserServiceContract extends GenericService<User, Long> implements 
    ReadService<User, Long>,
    UpdateService<User, Long> {

    public UserServiceContract() {
        super(User.class, Long.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public User cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public User modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public abstract List<User> cercaConFiltri(Map<String, String> filtri);
    
    @PreAuthorize("hasRole('ADMIN')")
    public abstract User cercaPerEmail(String email);

    @PreAuthorize("hasRole('ADMIN') || #username == authentication.principal.username")
    public abstract User cercaPerUsername(String username);

    @PreAuthorize("hasRole('ADMIN')")
    public abstract User cercaPerCodiceFiscale(String codiceFiscale);

    @PreAuthorize("hasRole('ADMIN')")
    public abstract User cercaPerNomeECognome(String nome, String cognome);

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #idUser == authentication.principal.id")
    public abstract User modificaEmail(Long idUser, String email);

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #idUser == authentication.principal.id")
    public abstract User modificaUsername(Long idUser, String username);

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #idUser == authentication.principal.id")
    public abstract User modificaPassword(Long idUser, String password);

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #idUser == authentication.principal.id")
    public abstract User modificaDatiPersona(Long idUser, Map<String, String> data);

}