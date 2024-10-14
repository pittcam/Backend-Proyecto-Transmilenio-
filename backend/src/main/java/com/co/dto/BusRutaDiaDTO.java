package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusRutaDiaDTO {
    private Long id;
    private BusDTO bus;
    private RutaDTO ruta;
    private List<String> dias;
}
