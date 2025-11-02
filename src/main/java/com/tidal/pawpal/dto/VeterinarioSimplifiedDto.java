package com.tidal.pawpal.dto;

import lombok.Data;

@Data
public class VeterinarioSimplifiedDto {

    private final Long id;
    private final String nome;
    private final String cognome;
    private final String specializzazione;
    private final String indirizzoStudio;

}
