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
public class BusRutaDiaDTO {

    private Long id;
    private Long rutaId;  // Identificador de la ruta
    private Set<Character> dias;  // Días específicos en los que el bus cumple esta ruta
}
