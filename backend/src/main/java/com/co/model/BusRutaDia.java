package com.co.model;

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
public class BusRutaDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    @ElementCollection
    @CollectionTable(name = "bus_ruta_dias", joinColumns = @JoinColumn(name = "bus_ruta_dia_id"))
    @Column(name = "dia")  // Aquí se almacenarán los días como caracteres
    private List<String> dias;
}
