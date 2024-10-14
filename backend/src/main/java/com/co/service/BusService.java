package com.co.service;

import com.co.dto.BusDTO;
import com.co.dto.ConductorDTO;
import com.co.dto.RutaDTO;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.repository.BusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todos los buses
    public List<BusDTO> getAllBuses() {
        return busRepository.findAll().stream()
                .map(bus -> modelMapper.map(bus, BusDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener buses disponibles
    public List<BusDTO> getBusesDisponibles() {
        return busRepository.findBusesDisponibles().stream()
                .map(bus -> modelMapper.map(bus, BusDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un bus por ID y mapear las rutas
    public BusDTO getBus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));

        // Mapear el Bus y sus rutas
        BusDTO busDTO = modelMapper.map(bus, BusDTO.class);
        List<RutaDTO> rutasDTO = bus.getRutas().stream()
                .map(ruta -> modelMapper.map(ruta, RutaDTO.class))
                .collect(Collectors.toList());

        busDTO.setRutas(rutasDTO);  // Asegúrate de que las rutas se añaden al DTO
        return busDTO;
    }


    // Crear un nuevo bus
    public BusDTO createBus(BusDTO busDTO) {
        Bus bus = modelMapper.map(busDTO, Bus.class);
        bus = busRepository.save(bus);
        return modelMapper.map(bus, BusDTO.class);
    }

    // Guardar (crear o actualizar) un bus
    public BusDTO save(BusDTO busDTO) {
        Bus bus = modelMapper.map(busDTO, Bus.class);
        bus = busRepository.save(bus);
        return modelMapper.map(bus, BusDTO.class);
    }

    // Eliminar un bus
    public void delete(Long id) {
        if (!busRepository.existsById(id)) {
            throw new RuntimeException("Bus no encontrado para eliminar");
        }
        busRepository.deleteById(id);
    }
}

