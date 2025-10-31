package com.tidal.pawpal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecensioneDto {
    
    private final Long idRecensione;
    private final Long idUtente;
    private final Integer votoRecensione;
    private final String commentoRecensione;
    private final LocalDate dataRecensione;
    private final String usernameUtente;
    private final String nomeUtente;
    private final String cognomeUtente;

}