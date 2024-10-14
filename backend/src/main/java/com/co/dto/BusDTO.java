package com.co.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class BusDTO {
    private Long id;
    private String placa;
    private String modelo;
    private List<RutaDTO> rutas;
}
