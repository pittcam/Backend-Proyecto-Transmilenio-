package com.co.service;

import com.co.dto.EstacionDTO;
import com.co.conversion.EstacionDTOConverter;
import com.co.repository.EstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private EstacionDTOConverter estacionDTOConverter;

    public List<EstacionDTO> obtenerEstaciones() {
        List<Estacion> estaciones = estacionRepository.findAll();
        return estaciones.stream()
                .map(estacionDTOConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
