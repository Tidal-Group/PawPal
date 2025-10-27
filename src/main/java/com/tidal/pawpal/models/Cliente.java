package com.tidal.pawpal.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "clienti")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends User {

private List<Recensione> recensioni;

}
