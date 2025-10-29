package com.tidal.pawpal.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "amministratori")
@PrimaryKeyJoinColumn(name = "amministratore_id")
public class Amministratore extends User {

}
