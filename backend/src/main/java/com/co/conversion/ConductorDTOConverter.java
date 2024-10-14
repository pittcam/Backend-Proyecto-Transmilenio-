package com.co.conversion;

import org.springframework.stereotype.Component;

import com.co.dto.ConductorDTO;
import com.co.model.Conductor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConductorDTOConverter {

    // Convertir de entidad a DTO
    public ConductorDTO entityToDTO(Conductor conductor) {
        return new ConductorDTO(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getCedula(),
                conductor.getTelefono(),
                conductor.getDireccion()
        );
    }

    // Metodo para convertir una lista de Conductores a una lista de ConductorDTOs
    public List<ConductorDTO> entitiesToDTOs(List<Conductor> conductores) {
        return conductores.stream()
                .map(this::entityToDTO)  // Convierte cada conductor en ConductorDTO
                .collect(Collectors.toList());  // Colecta los resultados en una lista
    }

    // Convertir de DTO a entidad
    public Conductor DTOToEntity(ConductorDTO conductor) {
        return new Conductor(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getCedula(),
                conductor.getTelefono(),
                conductor.getDireccion(),
                new HashSet<>() // Asignaciones se inicializa como un conjunto vacío
        );
    }
}
