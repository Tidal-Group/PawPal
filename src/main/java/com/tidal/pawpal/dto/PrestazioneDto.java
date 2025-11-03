package com.tidal.pawpal.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PrestazioneDto {

    private final Long id;
    private final String descrizione;
    private final BigDecimal prezzo;
    private final int durataVisita;

}
