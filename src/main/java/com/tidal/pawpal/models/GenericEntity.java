package com.tidal.pawpal.models;

import lombok.Data;

@Data
public abstract class GenericEntity implements IMappable{

    public abstract Long getId();
    public abstract void setId(Long id);

}
