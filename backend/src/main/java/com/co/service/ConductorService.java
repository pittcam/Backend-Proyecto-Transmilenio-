package com.co.service;

import com.co.dto.ConductorDTO;
import com.co.model.Conductor;
import com.co.repository.ConductorRepository;
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
    private ModelMapper modelMapper;  // Usar ModelMapper en lugar del DTOConverter

    // Obtener todos los conductores como DTO
    public List<ConductorDTO> getAllConductores() {
        List<Conductor> conductores = conductorRepository.findAll();
        return conductores.stream()
                .map(conductor -> modelMapper.map(conductor, ConductorDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un conductor por ID
    public ConductorDTO getConductor(Long id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        return modelMapper.map(conductor, ConductorDTO.class);
    }

    // Crear un nuevo conductor
    public ConductorDTO createConductor(ConductorDTO conductorDTO) {
        Conductor conductor = modelMapper.map(conductorDTO, Conductor.class);
        conductor = conductorRepository.save(conductor);
        return modelMapper.map(conductor, ConductorDTO.class);
    }

    // Guardar (crear o actualizar) un conductor
    public ConductorDTO saveConductor(ConductorDTO conductorDTO) {
        Conductor conductor = modelMapper.map(conductorDTO, Conductor.class);
        conductor = conductorRepository.save(conductor);
        return modelMapper.map(conductor, ConductorDTO.class);
    }

    // Buscar conductores por nombre
    public List<ConductorDTO> buscarConductoresPorNombre(String nombre) {
        List<Conductor> conductores = conductorRepository.findAllByNombreContainingIgnoreCase(nombre);
        return conductores.stream()
                .map(conductor -> modelMapper.map(conductor, ConductorDTO.class))
                .collect(Collectors.toList());
    }

    // Eliminar un conductor por ID
    public void deleteConductor(Long id) {
        if (!conductorRepository.existsById(id)) {
            throw new RuntimeException("Conductor no encontrado para eliminar");
        }
        conductorRepository.deleteById(id);
    }
}
