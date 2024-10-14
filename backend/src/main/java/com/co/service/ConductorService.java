package com.co.service;

import com.co.conversion.ConductorDTOConverter;
import com.co.dto.ConductorDTO;
import com.co.model.Conductor;
import com.co.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private ConductorDTOConverter conductorDTOConverter;

    public List<Conductor> getAllConductores() {
        return conductorRepository.findAll();
    }

    public ConductorDTO getConductor(Long id) {
        return conductorDTOConverter.entityToDTO(conductorRepository.findById(id).orElseThrow());
    }


    public ConductorDTO createConductor(ConductorDTO conductorDTO) {
        Conductor conductor = conductorDTOConverter.DTOToEntity(conductorDTO);
        return conductorDTOConverter.entityToDTO(conductorRepository.save(conductor));
    }


    // Crear o actualizar un conductor
    public ConductorDTO saveConductor(ConductorDTO conductorDTO) {
        Conductor conductor = conductorDTOConverter.DTOToEntity(conductorDTO);
        return conductorDTOConverter.entityToDTO(conductorRepository.save(conductor));
    }

    // Buscar conductor por nombre
    public List<ConductorDTO> buscarConductoresPorNombre(String nombre) {
        List<Conductor> conductores = conductorRepository.findAllByNombreContainingIgnoreCase(nombre);
        return conductorDTOConverter.entitiesToDTOs(conductores);  // Usa el nuevo método para convertir la lista
    }

    // Eliminar un conductor por ID
    public void deleteConductor(Long id) {
        if (!conductorRepository.existsById(id)) {
            throw new RuntimeException("Conductor no encontrado para eliminar");
        }
        conductorRepository.deleteById(id);
    }
}
