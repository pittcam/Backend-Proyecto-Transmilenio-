package com.co.transmilenio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {

    private Long id;
    private String modelo;
    private String placa;
    private List<Character> diasFuncionamiento;
    private List<BusRutaDiaDTO> rutasDias;  // Relación Bus-Ruta-Días en formato DTO
}
