package com.co.conversion;

import org.springframework.stereotype.Component;
import com.co.dto.BusDTO;
import com.co.model.Bus;

import java.util.HashSet;

@Component
public class BusDTOConverter {

    // Convertir de entidad a DTO
    public BusDTO entityToDTO(Bus bus) {
        return new BusDTO(
                bus.getId(),
                bus.getNumeroPlaca(),
                bus.getModelo()
        );
    }

    // Convertir de DTO a entidad
    public Bus DTOToEntity(BusDTO bus) {
        return new Bus(
                bus.getId(),
                bus.getNumeroPlaca(),
                bus.getModelo(),
                new HashSet<>() // Asignaciones se inicializa como un conjunto vacío
        );
    }
}
