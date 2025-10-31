package com.tidal.pawpal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppuntamentoDto {
    
    private final Long idAppuntamento;
    private final Long idUtente;
    private final LocalDateTime dataOraAppuntamento;
    private final String noteAppuntamento;
    private final String nomeUtente;
    private final String cognomeUtente;
    private final String telefonoUtente;
    private final String indirizzoUtente;

}
