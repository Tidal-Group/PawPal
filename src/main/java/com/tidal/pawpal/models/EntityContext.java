package com.tidal.pawpal.models;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EntityContext {
    
    @Bean
    @Scope("prototype")
    public Persona persona(Map<String,String> map){
        Persona p = new Persona();
        p.fromMap(map);
        return p;
    }

     @Bean
     @Scope("prototype")
    public User user(Map<String,String> map) {
        User u = new User();
        u.fromMap(map);
        return u;
    }

     @Bean
    public Amministratore amministratore(Map<String,String> map) {
        Amministratore a = new Amministratore();
        a.fromMap(map);
        return a;
    }

    @Bean
    @Scope("prototype")
    public Veterinario veterinario(Map<String,String> map) {
        Veterinario v = new Veterinario();
        v.fromMap(map);

        if (v.getRecensioni() == null) v.setRecensioni(new ArrayList<>());

        if (v.getDisponibilita() == null) v.setDisponibilita(new ArrayList<>());

        if (v.getAppuntamenti() == null) v.setAppuntamenti(new ArrayList<>());

        if (v.getSpecieTrattate() == null) v.setSpecieTrattate(new HashSet<>());

        if (v.getPrestazioniOfferte() == null) v.setPrestazioniOfferte(new HashSet<>());


        // if (map != null) {
        //     Map<Integer, Map<String, String>> bucket = new TreeMap<>();
        //     for (Map.Entry<String, String> e : map.entrySet()) {
        //         String k = e.getKey();
        //         if (k.startsWith("recensioni[")) {
        //             int i1 = k.indexOf('[') + 1;
        //             int i2 = k.indexOf(']');
        //             int idx = Integer.parseInt(k.substring(i1, i2));
        //             int dot = k.indexOf("].");
        //             String childKey = (dot >= 0 && dot + 2 < k.length()) ? k.substring(dot + 2) : "";
        //             bucket.computeIfAbsent(idx, __ -> new HashMap<>())
        //                   .put(childKey, e.getValue());
        //         }
        //     }
        //     if (!bucket.isEmpty()) {
        //         List<Recensione> lista = new ArrayList<>();
        //         for (Map<String, String> childMap : bucket.values()) {
        //             lista.add(recensione(childMap)); // crea Bean Recensione da fromMap
        //         }
        //         v.setRecensioni(lista);
        //     }
        // }

        return v;
    }

    @Bean
    @Scope("prototype")
    public Cliente cliente(Map<String,String> map) {
        Cliente c = new Cliente();
        c.fromMap(map);

        if (c.getRecensioni() == null) c.setRecensioni(new ArrayList<>());

        if (c.getAppuntamenti() == null) c.setAppuntamenti(new ArrayList<>());

        return c;
    }

    @Bean
    @Scope("prototype")
    public Appuntamento appuntamento(Map<String,String> map) {
        Appuntamento a = new Appuntamento();
        a.fromMap(map);
        return a;
    }

    @Bean
    @Scope("prototype")
    public Recensione recensione(Map<String,String> map) {
        Recensione r = new Recensione();
        r.fromMap(map);
        return r;
    }

    @Bean
    @Scope("prototype")
    public Disponibilita disponibilita(Map<String,String> map) {
        Disponibilita d = new Disponibilita();
        d.fromMap(map);
        return d;
    }

    @Bean
    @Scope("prototype")
    public Prestazione prestazione(Map<String,String> map) {
        Prestazione p = new Prestazione();
        p.fromMap(map);

        if (p.getVeterinari() == null) p.setVeterinari(new ArrayList<>());

        return p;
    }

    @Bean
    @Scope("prototype")
    public Specie specie(Map<String,String> map) {
        Specie s = new Specie();
        s.fromMap(map);

        if (s.getVeterinari() == null) s.setVeterinari(new ArrayList<>());

        return s;
    }

    
}
