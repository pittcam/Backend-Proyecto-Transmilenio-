package com.co.transmilenio.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String horaInicio;

    private String horaFin;

    @ElementCollection
    private List<Character> dias;

    @ManyToMany
    @JoinTable(
            name = "ruta_estacion",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "estacion_id")
    )
    private Set<Estacion> estaciones = new HashSet<>();


    // Validación de que la hora fin no puede ser menor o igual a hora inicio
    public void setHoraFin(String horaFin) {
        if (horaInicio != null && horaFin.compareTo(horaInicio) <= 0) {
            throw new IllegalArgumentException("La hora fin debe ser mayor a la hora inicio");
        }
        this.horaFin = horaFin;
    }
}