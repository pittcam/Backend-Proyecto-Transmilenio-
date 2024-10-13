package com.co.transmilenio.model;

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
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    @Column(unique = true, nullable = false)
    private String placa;

    @ElementCollection
    private List<Character> diasFuncionamiento;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<BusRutaDia> rutasDias = new ArrayList<>();  // Relación Bus-Ruta-Días
}
