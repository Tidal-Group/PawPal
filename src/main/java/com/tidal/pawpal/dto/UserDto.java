package com.tidal.pawpal.dto;

import lombok.Data;

@Data
public class UserDto {

    private final Long id;
    private final String nome;
    private final String cognome;
    private final String codiceFiscale;
    private final String telefono;
    private final String email;
    private final String username;

}
