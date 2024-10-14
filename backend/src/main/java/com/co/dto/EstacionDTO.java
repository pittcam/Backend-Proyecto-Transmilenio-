package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstacionDTO {

    private Long id;
    private String nombre;

    public void setId(Long id) { this.id = id; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
