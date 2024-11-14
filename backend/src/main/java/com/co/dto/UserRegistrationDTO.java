package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationDTO {
    private String nombre;
    private String cedula;
    private String correo;
    private String contrasena ;

    public UserRegistrationDTO() {
    }







}
