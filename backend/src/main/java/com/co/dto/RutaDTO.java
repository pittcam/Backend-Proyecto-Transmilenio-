package com.co.dto;

import lombok.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RutaDTO {
    private Long id;
    private String nombre;
    private Set<Long> estacionesIds;
    private String horaInicio;
    private String horaFin;
    private List<Character> dias;
}
