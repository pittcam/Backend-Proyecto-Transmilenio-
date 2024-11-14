package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String nombre;
    private String cedula;
    private String correo;
    private String contrasena;
    private String rol;

    public UserDTO() {

    }

}
