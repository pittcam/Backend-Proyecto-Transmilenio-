package com.co.transmilenio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignacion")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conductor conductor;

    @ManyToMany
    @JoinTable(
            name = "asignacion_bus",
            joinColumns = @JoinColumn(name = "asignacion_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private List<Bus> buses;
}
