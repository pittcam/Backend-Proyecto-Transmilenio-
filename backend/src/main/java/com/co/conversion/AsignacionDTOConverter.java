package com.co.conversion;

import com.co.model.*;
import org.springframework.stereotype.Component;

import com.co.dto.AsignacionDTO;

@Component
public class AsignacionDTOConverter {

    public AsignacionDTO entityToDTO(Asignacion asignacion) {
        return new AsignacionDTO(
                asignacion.getId(),
                asignacion.getBus() != null ? asignacion.getBus().getId() : null,
                asignacion.getConductor() != null ? asignacion.getConductor().getId() : null,
                asignacion.getRuta() != null ? asignacion.getRuta().getId() : null,
                asignacion.getHorario() != null ? asignacion.getHorario().getId() : null
        );
    }

    public Asignacion DTOToEntity(AsignacionDTO asignacionDTO) {
        return new Asignacion(
                asignacionDTO.getId(),
                asignacionDTO.getBusId() != null ? new Bus() : null,
                asignacionDTO.getConductorId() != null ? new Conductor() : null,
                asignacionDTO.getRutaId() != null ? new Ruta() : null,
                asignacionDTO.getHorarioId() != null ? new Horario() : null
        );
    }
}
