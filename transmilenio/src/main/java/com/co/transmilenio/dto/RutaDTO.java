package com.co.transmilenio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RutaDTO {
    private Long id;
    private String nombre;
    private String horaInicio;
    private String horaFin;
    private Set<Long> estacionesIds;  // Solo los IDs de las estaciones
    private Set<Character> dias;
}
