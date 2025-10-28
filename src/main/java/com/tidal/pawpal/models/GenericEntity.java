package com.tidal.pawpal.models;

import lombok.Data;

@Data
public abstract class GenericEntity implements IMappable{

    // DEBUG: decidere come e dove implementare questo metodo.
    // necessario per services/abstractions/CreateService.java
    public abstract void setId(Long id);

}
