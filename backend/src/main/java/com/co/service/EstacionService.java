package com.co.service;

import com.co.dto.EstacionDTO;
import com.co.model.Estacion;
import com.co.repository.EstacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private ModelMapper modelMapper;  // Usar ModelMapper en lugar del DTOConverter

    // Obtener todas las estaciones
    public List<EstacionDTO> obtenerEstaciones() {
        List<Estacion> estaciones = estacionRepository.findAll();
        return estaciones.stream()
                .map(estacion -> modelMapper.map(estacion, EstacionDTO.class))
                .collect(Collectors.toList());
    }
}
