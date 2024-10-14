package com.co.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class BusDTO {
    private Long id;
    private String numeroPlaca;
    private String modelo;
    private Set<RutaDTO> rutas;
}
