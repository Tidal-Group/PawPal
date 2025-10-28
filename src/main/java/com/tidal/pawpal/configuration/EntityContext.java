package com.tidal.pawpal.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Scope("prototype")
    public Persona persona(Map<String,String> map){
        Persona p = new Persona();
        p.fromMap(map, true);
        return p;
    }

    @Bean("personaMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Persona personaMerger(Persona p, Map<String, String> map) {
        p.fromMap(map, false);
        return p;
    }

    @Bean
    @Scope("prototype")
    public User user(Map<String,String> map) {
        User u = new User();
        u.fromMap(map, true);
        return u;
    }

    @Bean("userMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public User userMerger(User u, Map<String, String> map) {
        u.fromMap(map, false);
        return u;
    }

    @Bean
    public Amministratore amministratore(Map<String,String> map) {
        Amministratore a = new Amministratore();
        a.fromMap(map, true);
        return a;
    }

    @Bean
    @Scope("prototype")
    public Veterinario veterinario(Map<String,String> map) {
        Veterinario v = new Veterinario();
        v.fromMap(map, true);

        if (v.getRecensioni() == null) v.setRecensioni(new ArrayList<>());

        if (v.getDisponibilita() == null) v.setDisponibilita(new ArrayList<>());

        if (v.getAppuntamenti() == null) v.setAppuntamenti(new ArrayList<>());

        if (v.getSpecieTrattate() == null) v.setSpecieTrattate(new HashSet<>());

        if (v.getPrestazioniOfferte() == null) v.setPrestazioniOfferte(new HashSet<>());

        return v;
    }

    @Bean("veterinarioMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Veterinario veterinarioMerger(Veterinario v, Map<String, String> map) {
        v.fromMap(map, false);
        return v;
    }

    @Bean
    @Scope("prototype")
    public Cliente cliente(Map<String,String> map) {
        Cliente c = new Cliente();
        c.fromMap(map, true);

        if (c.getRecensioni() == null) c.setRecensioni(new ArrayList<>());

        if (c.getAppuntamenti() == null) c.setAppuntamenti(new ArrayList<>());

        return c;
    }

    @Bean("clienteMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Cliente clienteMerger(Cliente c, Map<String, String> map) {
        c.fromMap(map, false);
        return c;
    }

    @Bean
    @Scope("prototype")
    public Appuntamento appuntamento(Map<String,String> map) {
        Appuntamento a = new Appuntamento();
        a.fromMap(map, true);
        return a;
    }

    @Bean("appuntamentoMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Appuntamento appuntamentoMerger(Appuntamento a, Map<String, String> map) {
        a.fromMap(map, false);
        return a;
    }

    @Bean
    @Scope("prototype")
    public Recensione recensione(Map<String,String> map) {
        Recensione r = new Recensione();
        r.fromMap(map, true);
        return r;
    }

    @Bean("recensioneMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Recensione recensioneMerger(Recensione r, Map<String, String> map) {
        r.fromMap(map, false);
        return r;
    }

    @Bean
    @Scope("prototype")
    public Disponibilita disponibilita(Map<String,String> map) {
        Disponibilita d = new Disponibilita();
        d.fromMap(map, true);
        return d;
    }

    @Bean("disponibilitaMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Disponibilita disponibilitaMerger(Disponibilita d, Map<String, String> map) {
        d.fromMap(map, false);
        return d;
    }

    @Bean
    @Scope("prototype")
    public Prestazione prestazione(Map<String,String> map) {
        Prestazione p = new Prestazione();
        p.fromMap(map, true);

        if (p.getVeterinari() == null) p.setVeterinari(new ArrayList<>());

        return p;
    }

    @Bean("prestazioneMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Prestazione prestazioneMerger(Prestazione p, Map<String, String> map) {
        p.fromMap(map, false);
        return p;
    }

    @Bean
    @Scope("prototype")
    public Specie specie(Map<String,String> map) {
        Specie s = new Specie();
        s.fromMap(map, true);

        if (s.getVeterinari() == null) s.setVeterinari(new ArrayList<>());

        return s;
    }

    @Bean("specieMerger")
    @Qualifier("merger")
    @Scope("prototype")
    public Specie specieMerger(Specie s, Map<String, String> map) {
        s.fromMap(map, false);
        return s;
    }
    
}
