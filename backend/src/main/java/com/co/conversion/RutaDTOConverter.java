package com.co.conversion;

import org.springframework.stereotype.Component;
import com.co.dto.RutaDTO;
import com.co.model.Ruta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RutaDTOConverter {

    public RutaDTO entityToDTO(Ruta ruta) {
        Set<Long> estacionesIds = ruta.getEstaciones().stream()
                .map(Estacion::getId)
                .collect(Collectors.toSet());
        return new RutaDTO(
                ruta.getId(),
                ruta.getNombre(),
                estacionesIds,
                ruta.getHorarioFuncionamiento()
        );
    }

    // Metodo para convertir una lista de rutas a una lista de ConductorDTOs
    public List<RutaDTO> entitiesToDTOs(List<Ruta> rutas) {
        return rutas.stream()
                .map(this::entityToDTO)  // Convierte cada ruta en ConductorDTO
                .collect(Collectors.toList());  // Colecta los resultados en una lista
    }

    public Ruta dtoToEntity(RutaDTO rutaDTO, Set<Estacion> estaciones) {
        Ruta ruta = new Ruta();
        ruta.setId(rutaDTO.getId());
        ruta.setNombre(rutaDTO.getNombre());
        ruta.setHorarioFuncionamiento(rutaDTO.getHorarioFuncionamiento());
        ruta.setEstaciones(estaciones);
        return ruta;
    }
}
