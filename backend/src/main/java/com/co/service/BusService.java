package com.co.service;

import com.co.conversion.BusDTOConverter;
import com.co.dto.BusDTO;
import com.co.dto.ConductorDTO;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusDTOConverter busDTOConverter;

    // Obtener todos los buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public List<Bus> getBusesDisponibles() {
        List<Bus> disponibles = busRepository.findBusesDisponibles();
        System.out.println("Buses disponibles: " + disponibles);  // Añade este log
        return disponibles;
    }

    // Obtener un bus por ID
    public BusDTO getBus(Long id) {
        return busDTOConverter.entityToDTO(busRepository.findById(id).orElseThrow());
    }



    // Crear o actualizar un bus
    public BusDTO save(BusDTO busDTO) {
        Bus bus = busDTOConverter.DTOToEntity(busDTO);
        return busDTOConverter.entityToDTO(busRepository.save(bus));
    }

    // Eliminar un bus por ID
    public void delete(Long id) {
        if (!busRepository.existsById(id)) {
            throw new RuntimeException("Bus no encontrado para eliminar");
        }
        busRepository.deleteById(id);
    }



    public BusDTO createBus(BusDTO busDTO) {
        Bus bus = busDTOConverter.DTOToEntity(busDTO);
        return busDTOConverter.entityToDTO(busRepository.save(bus));
    }

    public List<Bus> findByIds(List<Long> ids) {
        return busRepository.findAllById(ids);
    }
}

