package com.co.service;

import com.co.dto.BusDTO;
import com.co.dto.ConductorDTO;
import com.co.dto.RutaDTO;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.model.Ruta;
import com.co.repository.BusRepository;
import com.co.repository.RutaRepository;
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
    private RutaRepository rutaRepository;

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
        return busRepository.findById(id)
                .map(bus -> {
                    BusDTO busDTO = modelMapper.map(bus, BusDTO.class);
                    List<RutaDTO> rutasDTO = bus.getRutas().stream()
                            .map(ruta -> modelMapper.map(ruta, RutaDTO.class))
                            .collect(Collectors.toList());
                    busDTO.setRutas(rutasDTO);
                    return busDTO;
                })
                .orElseThrow(() -> new RuntimeException("Bus no encontrado con ID: " + id));
    }


    // Asignar varias rutas a un bus
    public BusDTO asignarRutas(Long busId, List<Long> rutaIds) {
        // Buscar el bus por ID
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));

        // Obtener las rutas actuales del bus
        List<Ruta> rutasActuales = bus.getRutas();

        // Obtener las nuevas rutas por sus IDs
        List<Ruta> nuevasRutas = rutaRepository.findAllById(rutaIds);

        // Combinar las rutas actuales con las nuevas (sin duplicados)
        for (Ruta nuevaRuta : nuevasRutas) {
            if (!rutasActuales.contains(nuevaRuta)) {
                rutasActuales.add(nuevaRuta); // Agregar solo si no está ya presente
            }
        }

        // Guardar el bus con las rutas actualizadas
        bus.setRutas(rutasActuales); // Actualizar las rutas del bus
        bus = busRepository.save(bus); // Guardar el bus actualizado

        // Retornar el bus actualizado
        return modelMapper.map(bus, BusDTO.class);
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

