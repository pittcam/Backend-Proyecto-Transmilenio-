package com.co.transmilenio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConductorDTO {
    private Long id;
    private String nombre;
    private String cedula;
    private String telefono;
    private String direccion;
}

