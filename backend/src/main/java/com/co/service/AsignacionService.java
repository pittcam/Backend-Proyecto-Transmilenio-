package com.co.service;

import com.co.dto.AsignacionDTO;
import com.co.dto.BusDTO;
import com.co.dto.BusRutaDiaDTO;
import com.co.dto.RutaDTO;
import com.co.model.*;
import com.co.repository.AsignacionRepository;
import com.co.repository.BusRepository;
import com.co.repository.ConductorRepository;
import com.co.repository.RutaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las asignaciones
    public List<Asignacion> obtenerTodos() {
        return asignacionRepository.findAll();
    }

    // Obtener una asignación por ID
    public Optional<Asignacion> obtenerPorId(Long id) {
        return asignacionRepository.findById(id);
    }

    // Guardar una nueva asignación
    public Asignacion guardar(AsignacionDTO asignacionDTO) {
        Asignacion asignacion = modelMapper.map(asignacionDTO, Asignacion.class);
        return asignacionRepository.save(asignacion);
    }

    // Obtener los buses asignados a un conductor
    public List<BusDTO> obtenerBusesPorConductorId(Long conductorId) {
        List<Asignacion> asignaciones = asignacionRepository.findByConductorId(conductorId);
        return asignaciones.stream()
                .flatMap(asignacion -> asignacion.getBusRutaDias().stream()) // Obtiene los BusRutaDia
                .map(busRutaDia -> modelMapper.map(busRutaDia.getBus(), BusDTO.class)) // Mapea los buses a DTO
                .distinct() // Evita duplicados si el mismo bus está en múltiples asignaciones
                .collect(Collectors.toList());
    }

    // Asignar ruta a un bus
    public Asignacion asignarRutaABus(Long busId, Long rutaId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        // Crear BusRutaDia para asignar la ruta y el bus
        BusRutaDia busRutaDia = new BusRutaDia();
        busRutaDia.setBus(bus);
        busRutaDia.setRuta(ruta);

        // Crear una nueva asignación con el BusRutaDia
        Asignacion asignacion = new Asignacion();
        asignacion.setBusRutaDias(List.of(busRutaDia)); // Asigna el busRutaDia a la asignación
        return asignacionRepository.save(asignacion);
    }

    // Eliminar una asignación por ID
    public void eliminar(Long id) {
        asignacionRepository.deleteById(id);
    }

    // Verificar si se puede eliminar un bus
    public boolean puedeEliminarBus(Long busId) {
        List<Asignacion> asignaciones = asignacionRepository.findByBusId(busId); // Se obtiene a través de BusRutaDia

        for (Asignacion asignacion : asignaciones) {
            // Si en alguna asignación de BusRutaDia el bus tiene un conductor asignado, no se puede eliminar
            if (asignacion.getBusRutaDias().stream().anyMatch(busRutaDia -> busRutaDia.getBus().getId().equals(busId)
                    && asignacion.getConductor() != null)) {
                return false; // No se puede eliminar porque tiene un conductor asignado
            }
        }
        return true;
    }
}

