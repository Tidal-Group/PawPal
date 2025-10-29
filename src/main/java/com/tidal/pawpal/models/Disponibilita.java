package com.tidal.pawpal.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "disponibilita")
public class Disponibilita extends GenericEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Enumerated(EnumType.STRING) 
    // private GiornoSettimana giornoSettimana;
    @Column(name = "giorno_Settimana", nullable = false)
    private String giornoSettimana;

    @Column(name = "ora_Inizio", nullable = false)
    private LocalTime oraInizio;

    @Column(name = "ora_Fine", nullable = false)
    private LocalTime oraFine;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "veterinario_id", nullable = false)
    @ToString.Exclude
    private  Veterinario veterinario;

}
