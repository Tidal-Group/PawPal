package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class UserServiceContract extends GenericService<User, Long> implements
    CreateService<User, Long>, 
    ReadService<User, Long>,
    UpdateService<User, Long>,
    DeleteService<User, Long> {

    public UserServiceContract() {
        super(User.class, Long.class);
    }

    public abstract List<User> cercaConFiltri(Map<String, String> filtri);
    public abstract User cercaPerEmail(String email);
    public abstract User cercaPerUsername(String username);
    public abstract User cercaPerCodiceFiscale(String codiceFiscale);
    public abstract User cercaPerNomeECognome(String nome, String cognome);

    public abstract User modificaEmail(Long idUser, String email);
    public abstract User modificaUsername(Long idUser, String username);
    public abstract User modificaPassword(Long idUser, String password);

    public abstract User modificaDatiPersona(Long idUser, Map<String, String> data);

}