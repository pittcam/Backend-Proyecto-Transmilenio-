package com.co.conversion;

import com.co.dto.EstacionDTO;
import org.springframework.stereotype.Component;

@Component
public class EstacionDTOConverter {

    public EstacionDTO entityToDTO(Estacion estacion) {
        return new EstacionDTO(estacion.getId(), estacion.getNombre());
    }

    public Estacion dtoToEntity(EstacionDTO estacionDTO) {
        Estacion estacion = new Estacion();
        estacion.setId(estacionDTO.getId());
        estacion.setNombre(estacionDTO.getNombre());
        return estacion;
    }
}
