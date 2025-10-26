package com.tidal.pawpal.services.contracts;

import com.tidal.pawpal.models.User;

import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;

public abstract class UserServiceContract extends GenericService<User, Long> implements
    CreateService<User, Long>, 
    ReadService<User, Long>,
    UpdateService<User, Long>,
    DeleteService<User, Long> {

    public UserServiceContract() {
        super(User.class, Long.class);
    }

    public abstract User cercaPerEmail(String email);
    public abstract User cercaPerUsername(String username);

    public abstract User modificaEmail(String email);
    public abstract User modificaUsername(String username);
    public abstract User modificaPassword(String password);

}