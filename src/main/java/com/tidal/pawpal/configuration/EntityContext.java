package com.tidal.pawpal.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.tidal.pawpal.models.Amministratore;
import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Disponibilita;
import com.tidal.pawpal.models.Persona;
import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;

@Configuration
public class EntityContext {
    
    @Bean
    @Primary
    @Scope("prototype")
    public Persona persona(Map<String,String> map){
        Persona p = new Persona();
        p.fromMap(map, true);
        return p;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public User user(Map<String,String> map) {
        User u = new User();
        u.fromMap(map, true);
        return u;
    }

    @Bean
    @Primary
    public Amministratore amministratore(Map<String,String> map) {
        Amministratore a = new Amministratore();
        a.fromMap(map, true);
        return a;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Veterinario veterinario(Map<String,String> map) {
        Veterinario v = new Veterinario();
        v.fromMap(map, true);

        if (v.getRecensioni() == null) v.setRecensioni(new ArrayList<>());

        // if (v.getDisponibilita() == null) v.setDisponibilita(new ArrayList<>());

        if (v.getAppuntamenti() == null) v.setAppuntamenti(new ArrayList<>());

        if (v.getSpecieTrattate() == null) v.setSpecieTrattate(new HashSet<>());

        if (v.getPrestazioniOfferte() == null) v.setPrestazioniOfferte(new HashSet<>());

        return v;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Cliente cliente(Map<String,String> map) {
        Cliente c = new Cliente();
        c.fromMap(map, true);

        if (c.getRecensioni() == null) c.setRecensioni(new ArrayList<>());

        if (c.getAppuntamenti() == null) c.setAppuntamenti(new ArrayList<>());

        return c;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Appuntamento appuntamento(Map<String,String> map) {
        Appuntamento a = new Appuntamento();
        a.fromMap(map, true);
        return a;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Recensione recensione(Map<String,String> map) {
        Recensione r = new Recensione();
        r.fromMap(map, true);
        return r;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Disponibilita disponibilita(Map<String,String> map) {
        Disponibilita d = new Disponibilita();
        d.fromMap(map, true);
        return d;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Prestazione prestazione(Map<String,String> map) {
        Prestazione p = new Prestazione();
        p.fromMap(map, true);

        if (p.getVeterinari() == null) p.setVeterinari(new ArrayList<>());

        return p;
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Specie specie(Map<String,String> map) {
        Specie s = new Specie();
        s.fromMap(map, true);

        if (s.getVeterinari() == null) s.setVeterinari(new ArrayList<>());

        return s;
    }
    
}
