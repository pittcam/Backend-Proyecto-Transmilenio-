package com.co.transmilenio.service;

import com.co.transmilenio.dto.AsignacionDTO;
import com.co.transmilenio.model.Asignacion;
import com.co.transmilenio.repository.AsignacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private ModelMapper modelMapper;  // Inyectamos el ModelMapper

    public List<AsignacionDTO> listarAsignaciones() {
        return asignacionRepository.findAll().stream()
                .map(asignacion -> modelMapper.map(asignacion, AsignacionDTO.class))  // Convertimos a DTO
                .collect(Collectors.toList());
    }

    public boolean existeAsignacionPorRuta(Long rutaId) {
        return asignacionRepository.existsByRutaId(rutaId);
    }

    public AsignacionDTO crearAsignacion(AsignacionDTO asignacionDTO) {
        Asignacion asignacion = modelMapper.map(asignacionDTO, Asignacion.class);  // Convertimos de DTO a entidad
        asignacion = asignacionRepository.save(asignacion);
        return modelMapper.map(asignacion, AsignacionDTO.class);  // Convertimos de vuelta a DTO
    }
}
