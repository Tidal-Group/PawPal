package com.tidal.pawpal.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recensioni")
public class Recensione extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voto")
    private int voto;

    @Column(name = "commento", length = 2000)
    private String commento;
    @Column(name = "data_Recensione")
    private LocalDate dataRecensione = LocalDate.now();
}
