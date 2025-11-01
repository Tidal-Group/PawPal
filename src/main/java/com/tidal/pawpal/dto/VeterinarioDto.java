package com.tidal.pawpal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarioDto extends UserDto {

    private final String specializzazione;
    private final String iscrizioneAlbo;
    private final String descrizione;
    private final String indirizzoStudio;
    private final String disponibilita;

    public VeterinarioDto(
        Long id,
        String nome,
        String cognome,
        String codiceFiscale,
        String telefono,
        String email,
        String username,
        String specializzazione,
        String iscrizioneAlbo,
        String descrizione,
        String indirizzoStudio,
        String disponibilita
    ) {
        super(id, nome, cognome, codiceFiscale, telefono, email, username);
        this.specializzazione = specializzazione;
        this.iscrizioneAlbo = iscrizioneAlbo;
        this.descrizione = descrizione;
        this.indirizzoStudio = indirizzoStudio;
        this.disponibilita = disponibilita;
    }

}
