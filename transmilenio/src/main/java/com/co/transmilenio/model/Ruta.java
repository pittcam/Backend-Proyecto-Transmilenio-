package com.co.transmilenio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ruta")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String horaInicio;
    private String horaFin;

    @ElementCollection
    private Set<Character> dias;  // Días que opera la ruta

    @ManyToMany
    @JoinTable(
            name = "ruta_estacion",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "estacion_id")
    )
    private Set<Estacion> estaciones = new HashSet<>();

    // Validación de que horaFin sea mayor que horaInicio
    @PrePersist
    @PreUpdate
    private void validarHorario() {
        if (horaInicio.compareTo(horaFin) >= 0) {
            throw new IllegalArgumentException("Hora de fin debe ser mayor que hora de inicio");
        }
    }
}
