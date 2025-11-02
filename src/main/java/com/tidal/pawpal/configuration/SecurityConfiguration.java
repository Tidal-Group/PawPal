package com.tidal.pawpal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        RequestMatcher logoutGetAndPostMatcher = request -> 
            request.getServletPath().equals("/logout") && 
            (request.getMethod().equals("GET") || request.getMethod().equals("POST"));

        security
        .authorizeHttpRequests((authorizations) -> 
            authorizations
            .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**").permitAll()
            .requestMatchers("/", "/403", "/error", "/auth/**", "/contatti/**").permitAll()
            // researching veterinari should be permitted to everybody
            .requestMatchers("/veterinari/lista_veterinari").permitAll()
            .requestMatchers("/veterinari/inserisci_appuntamento", "/veterinari/inserisci_recensione").hasRole("CLIENTE")
            .requestMatchers("/dash/**").hasAnyRole("CLIENTE", "VETERINARIO")
            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception.accessDeniedPage("/public/403"))
        .formLogin((form) -> 
            form
            .loginPage("/")
            .loginProcessingUrl("/auth/login")
            .defaultSuccessUrl("/", true)
            .permitAll()
        )
        .sessionManagement(session -> 
            session
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        )
        .logout((logout) ->
            logout
            .logoutRequestMatcher(logoutGetAndPostMatcher)
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        );

        return security.build();
    }

}
