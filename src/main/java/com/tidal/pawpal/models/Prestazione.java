package com.tidal.pawpal.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "prestazioni")
public class Prestazione extends GenericEntity {

    

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo")
    private BigDecimal prezzo;

    @Column(name = "durata_Visita")
    private int durataVisita;

    @ManyToMany(mappedBy = "prestazioniOfferte")
    @ToString.Exclude
    private List<Veterinario> veterinari;

}
