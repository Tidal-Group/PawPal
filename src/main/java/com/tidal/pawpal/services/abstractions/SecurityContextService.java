package com.tidal.pawpal.services.abstractions;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityContextService {

    UserDetailsService getUserDetailsService();

    default void refreshSecurityContext(String newUsername) {

        UserDetails newUserDetails = getUserDetailsService().loadUserByUsername(newUsername);
        
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
            newUserDetails, 
            newUserDetails.getPassword(),
            newUserDetails.getAuthorities()
        );
        
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        
    }
}

