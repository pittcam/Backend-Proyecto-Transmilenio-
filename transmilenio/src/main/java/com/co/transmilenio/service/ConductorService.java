package com.co.transmilenio.service;

import com.co.transmilenio.dto.AsignacionDTO;
import com.co.transmilenio.dto.ConductorDTO;
import com.co.transmilenio.model.Bus;
import com.co.transmilenio.model.Conductor;
import com.co.transmilenio.repository.BusRepository;
import com.co.transmilenio.repository.ConductorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private BusService busService;

    @Autowired
    private ModelMapper modelMapper;

    public List<ConductorDTO> listarConductores() {
        return conductorRepository.findAll().stream()
                .map(conductor -> modelMapper.map(conductor, ConductorDTO.class))
                .collect(Collectors.toList());
    }

    public ConductorDTO obtenerConductorPorId(Long id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        return modelMapper.map(conductor, ConductorDTO.class);
    }

    public ConductorDTO crearConductor(ConductorDTO conductorDTO, List<Long> busesIds) {
        List<Bus> buses = busesIds.stream()
                .map(id -> busRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Bus no encontrado")))
                .collect(Collectors.toList());

        // Validar que los buses seleccionados no tengan rutas con conflictos
        busService.validarRutasNoCruzadasEntreBuses(buses);

        // Crear el conductor
        Conductor conductor = modelMapper.map(conductorDTO, Conductor.class);
        conductor = conductorRepository.save(conductor);

        // Crear asignación con los buses seleccionados
        asignacionService.crearAsignacion(new AsignacionDTO(null, conductor.getId(), busesIds));

        return modelMapper.map(conductor, ConductorDTO.class);
    }

    public ConductorDTO actualizarConductor(Long id, ConductorDTO conductorDTO) {
        Conductor conductorExistente = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        modelMapper.map(conductorDTO, conductorExistente);
        conductorRepository.save(conductorExistente);

        return modelMapper.map(conductorExistente, ConductorDTO.class);
    }

    public void eliminarConductor(Long id) {
        // Eliminar asignaciones del conductor antes de eliminarlo
        asignacionService.eliminarAsignacionesPorConductorId(id);
        conductorRepository.deleteById(id);
    }
}
