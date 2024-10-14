package com.co.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_placa", unique = true, nullable = false)
    private String placa;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @ManyToMany
    @JoinTable(
            name = "bus_ruta",
            joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "ruta_id")
    )
    private List<Ruta> rutas = new ArrayList<>();
}
