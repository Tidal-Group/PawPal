package com.tidal.pawpal.models;


import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
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

    /* The CascadeType.REMOVE on a Many-to-Many collection tells Hibernate: 
    "When I delete the parent entity, find all the rows in the join table 
    associated with this parent's ID and delete those rows too." 
    It does not proceed to delete the linked entity itself. */

    /* In short, if you only need deletion to cascade and want to be precise,
    use CascadeType.REMOVE. If you want deletion, creation,
    and updating of the relationship handled automatically, use CascadeType.ALL. */

    @Column(name="specializzazione") 
    private String specializzazione;
    @Column(name="iscrizione_Albo") 
    private String iscrizioneAlbo;

    @Column(name="descrizione") 
    private String descrizione;
    @Column(name="indirizzo_Studio") 
    private String indirizzoStudio;

    @OneToMany(mappedBy = "veterinario")
    private List<Disponibilita> disponibilita;

    @OneToMany(mappedBy = "veterinario")
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "veterinario")
    private List<Appuntamento> appuntamenti;

    @ManyToMany(cascade = {CascadeType.ALL}) // AGGIUNTA NUOVA
    @JoinTable(name = "veterinario_specie",
        joinColumns = @JoinColumn(name = "veterinario_id"),
        inverseJoinColumns = @JoinColumn(name = "specie_id"))
    private Set<Specie> specieTrattate;

    @ManyToMany(cascade = {CascadeType.ALL}) // AGGIUNTA NUOVA
    @JoinTable(name = "veterinario_prestazione",
        joinColumns = @JoinColumn(name = "veterinario_id"),
        inverseJoinColumns = @JoinColumn(name = "prestazione_id"))
    private Set<Prestazione> prestazioniOfferte;
}
