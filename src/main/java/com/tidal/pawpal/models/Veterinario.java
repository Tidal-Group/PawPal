package com.tidal.pawpal.models;


import java.util.List;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Entity
@Table(name = "veterinari")
@PrimaryKeyJoinColumn(name = "veterinario_id")
public class Veterinario extends User {

    @Column(name="specializzazione") 
    private String specializzazione;
    @Column(name="iscrizione_Albo") 
    private String iscrizioneAlbo;

    @Column(name="descrizione") 
    private String descrizione;
    @Column(name="indirizzo_Studio") 
    private String indirizzoStudio;

    @OneToMany(mappedBy = "veterinario")  //vedere meglio cascadeType
    private List<Disponibilita> disponibilita;

    @OneToMany(mappedBy = "veterinario")
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "veterinario")
    private List<Appuntamento> appuntamenti;

    @ManyToMany
    @JoinTable(name = "veterinario_specie",
        joinColumns = @JoinColumn(name = "veterinario_id"),
        inverseJoinColumns = @JoinColumn(name = "specie_id"))
    private Set<Specie> specieTrattate;

    @ManyToMany
    @JoinTable(name = "veterinario_prestazione",
        joinColumns = @JoinColumn(name = "veterinario_id"),
        inverseJoinColumns = @JoinColumn(name = "prestazione_id"))
    private Set<Prestazione> prestazioniOfferte;
}
