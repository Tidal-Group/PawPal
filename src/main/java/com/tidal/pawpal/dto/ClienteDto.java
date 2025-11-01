package com.tidal.pawpal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto extends UserDto {
    
    public ClienteDto(
        Long id,
        String nome,
        String cognome,
        String codiceFiscale,
        String telefono,
        String email,
        String username
    ) {
        super(id, nome, cognome, codiceFiscale, telefono, email, username);
    }

}
