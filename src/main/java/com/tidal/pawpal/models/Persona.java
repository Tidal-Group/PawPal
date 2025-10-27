package com.tidal.pawpal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  
@Table(name = "persone")
public class Persona extends GenericEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;

    private String nome;
    
    private String cognome;

    @Column(name = "codice_fiscale", length = 16, nullable = false, unique = true)
    private String codiceFiscale;

    private String telefono;

   

}
