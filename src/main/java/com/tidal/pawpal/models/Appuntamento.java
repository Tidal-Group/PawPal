package com.tidal.pawpal.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "appuntamenti")
public class Appuntamento extends GenericEntity {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "data_Ora",nullable = false)
  private LocalDateTime dataOra;

  @Column(name = "note", length = 1000)
  private String note;

  @ManyToOne(optional = true)
  @JoinColumn(name = "cliente_id", nullable = true)
  @ToString.Exclude
  private Cliente cliente;

  // anche se il veterinario viene cancellato,
  // l'appuntamento continuerà a essere visibile nella pagina
  // degli appuntamenti (passati) presente nella dashboard del cliente
  // che l'ha fatta
  @ManyToOne(optional = true)
  @JoinColumn(name = "veterinario_id", nullable = true)
  @ToString.Exclude
  private Veterinario veterinario;

}
