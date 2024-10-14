package com.co.dto;

import com.co.model.Estacion;
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
    private List<Long> estacionesIds;  // Volver a manejar IDs
    private String horaInicio;
    private String horaFin;
    private List<String> dias;
}
