package com.co.dto;

import com.co.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String correo;
    private Role role;

    public JwtAuthenticationResponse() {
    }

}
