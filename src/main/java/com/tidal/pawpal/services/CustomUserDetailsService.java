package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User utente = userRepository.findByUsername(username);
        
        if(utente == null) throw new UsernameNotFoundException("User " + username + " not found");

        return 
            org.springframework.security.core.userdetails.User
            .withUsername(utente.getUsername())
            .password("{noop}" + utente.getPassword())
            .roles(utente.getRuolo())
            .build();

    }

}
