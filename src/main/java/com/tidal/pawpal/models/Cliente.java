package com.tidal.pawpal.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Entity
@Table(name = "clienti")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends User {

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Appuntamento> appuntamenti;

}
