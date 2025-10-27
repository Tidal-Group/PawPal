package com.tidal.pawpal.models;

import java.time.LocalDateTime;

import javax.swing.text.html.parser.Entity;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Appuntamento extends GenericEntity {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  
  @Column(nullable = false)
  private LocalDateTime dataOra;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cliente_id", nullable = false)
  private Cliente cliente;

  @ManyToOne(optional = false)
  @JoinColumn(name = "veterinario_id", nullable = false)
  private Veterinario veterinario;

}
