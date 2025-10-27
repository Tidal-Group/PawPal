package com.tidal.pawpal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

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
}
