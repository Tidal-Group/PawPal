package com.tidal.pawpal.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "specie")
public class Specie extends GenericEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_Specie")
    private String nomeSpecie;

    @ManyToMany(mappedBy = "specieTrattate")
    private List<Veterinario> veterinari;

}
