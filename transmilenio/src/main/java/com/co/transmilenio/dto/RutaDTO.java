package com.co.transmilenio.dto;

import java.util.Set;

public class RutaDTO {

    private Long id;
    private String nombre;
    private Set<Long> estacionesIds; // Solo los IDs de las estaciones
    private Set<Long> horarioFuncionamiento; // IDs de horarios

    // Constructor, getters y setters
    public RutaDTO(Long id, String nombre, Set<Long> estacionesIds, Set<Long> horarioFuncionamiento) {
        this.id = id;
        this.nombre = nombre;
        this.estacionesIds = estacionesIds;
        this.horarioFuncionamiento = horarioFuncionamiento;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Long> getEstacionesIds() { return estacionesIds; }
    public void setEstacionesIds(Set<Long> estacionesIds) { this.estacionesIds = estacionesIds; }

    public Set<Long> getHorarioFuncionamiento() { return horarioFuncionamiento; }
    public void setHorarioFuncionamiento(Set<Long> horarioFuncionamiento) { this.horarioFuncionamiento = horarioFuncionamiento; }
}