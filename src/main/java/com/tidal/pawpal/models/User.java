package com.tidal.pawpal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "user_id")   
public class User extends Persona {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password; 

    @Column(name = "ruolo")
    private String ruolo;

}
