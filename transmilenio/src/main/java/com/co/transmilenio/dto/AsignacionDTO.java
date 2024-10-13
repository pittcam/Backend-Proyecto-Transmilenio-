package com.co.transmilenio.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AsignacionDTO {
    private Long id;
    private Long busId;        // ID del bus
    private Long conductorId;  // ID del conductor
    private Long rutaId;       // ID de la ruta
    private Long horarioId;    // ID del horario
}
