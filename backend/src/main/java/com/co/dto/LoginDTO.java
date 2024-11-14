package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginDTO {
    private String correo;
    private String contrasena;

    public LoginDTO() {
    }


}

