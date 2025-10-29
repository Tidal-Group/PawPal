package com.tidal.pawpal.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        return new SecuredUser(utente);

    }

    public static class SecuredUser implements UserDetails {
        
        private final Long id;
        private final String username;
        private final String password;
        private final Collection<? extends GrantedAuthority> authorities;

        public SecuredUser(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRuolo()));
        }

        public Long getId() {
            return id;
        }

        @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
        @Override public String getPassword() { return "{noop}" + password; }
        @Override public String getUsername() { return username; }
        @Override public boolean isAccountNonExpired() { return true; }
        @Override public boolean isAccountNonLocked() { return true; }
        @Override public boolean isCredentialsNonExpired() { return true; }
        @Override public boolean isEnabled() { return true; }
    }

}
