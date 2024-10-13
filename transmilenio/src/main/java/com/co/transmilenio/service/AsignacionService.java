package com.co.transmilenio.service;

import com.co.transmilenio.model.Asignacion;
import com.co.transmilenio.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    // Convertir de entidad a DTO
    private AsignacionDTO convertToDTO(Asignacion asignacion) {
        AsignacionDTO dto = new AsignacionDTO();
        dto.setId(asignacion.getId());
        dto.setRutaId(asignacion.getRuta().getId());
        dto.setConductorId(asignacion.getConductor().getId());
        dto.setBusId(asignacion.getBus().getId());
        return dto;
    }

    public List<AsignacionDTO> listarAsignaciones() {
        return asignacionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean existeAsignacionPorRuta(Long rutaId) {
        return asignacionRepository.existsByRutaId(rutaId);
    }

    public AsignacionDTO crearAsignacion(AsignacionDTO asignacionDTO) {
        Asignacion asignacion = new Asignacion();
        asignacion.setRuta(new Ruta(asignacionDTO.getRutaId()));
        asignacion.setConductor(new Conductor(asignacionDTO.getConductorId()));
        asignacion.setBus(new Bus(asignacionDTO.getBusId()));
        asignacion = asignacionRepository.save(asignacion);
        return convertToDTO(asignacion);
    }
}
