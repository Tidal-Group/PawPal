package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.CustomUserDetailsService;
import com.tidal.pawpal.services.UserService;

public abstract class AuthenticatedController {

    @Autowired
    protected UserService userService;

    protected static boolean isCliente(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));
    }

    protected static boolean isVeterinario(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VETERINARIO"));
    }

    protected void acceptAuthenticated(Principal principal, BiConsumer<Authentication, User> consumer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.SecuredUser securedUser = (CustomUserDetailsService.SecuredUser) authentication.getPrincipal();
        // IMPLEMENT: sostituire con un DTO
        User utente = userService.cercaPerId(securedUser.getId());
        consumer.accept(authentication, utente);
    }

}
