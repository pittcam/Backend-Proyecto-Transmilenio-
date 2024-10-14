package com.co.service;

import com.co.dto.AsignacionDTO;
import com.co.dto.BusDTO;
import com.co.dto.BusRutaDiaDTO;
import com.co.dto.RutaDTO;
import com.co.model.*;
import com.co.repository.*;
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
    private BusRutaDiaRepository busRutaDiaRepository;

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

    // Método para agregar un BusRutaDia a una asignación
    public AsignacionDTO agregarBusRutaDia(Long asignacionId, BusRutaDiaDTO busRutaDiaDTO) {
        // Buscar la asignación por ID
        Asignacion asignacion = asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // Mapear el DTO recibido a la entidad BusRutaDia
        BusRutaDia busRutaDia = modelMapper.map(busRutaDiaDTO, BusRutaDia.class);

        // Añadir el nuevo BusRutaDia a la lista de busRutaDias de la asignación
        asignacion.getBusRutaDias().add(busRutaDia);

        // Guardar la asignación actualizada en la base de datos
        Asignacion asignacionActualizada = asignacionRepository.save(asignacion);

        // Convertir la asignación actualizada a AsignacionDTO
        AsignacionDTO asignacionDTO = modelMapper.map(asignacionActualizada, AsignacionDTO.class);

        return asignacionDTO;  // Devolver el DTO resultante
    }

    // Obtener una asignación por el ID del conductor
    public Optional<AsignacionDTO> obtenerAsignacionPorConductor(Long conductorId) {
        List<Asignacion> asignaciones = asignacionRepository.findByConductorId(conductorId);

        if (asignaciones.isEmpty()) {
            return Optional.empty();
        }

        Asignacion asignacion = asignaciones.get(0);  // Solo devolvemos la primera asignación
        // Convertir la asignación a AsignacionDTO usando ModelMapper
        AsignacionDTO asignacionDTO = modelMapper.map(asignacion, AsignacionDTO.class);

        return Optional.of(asignacionDTO);
    }

    // Obtener lista de BusRutaDiaDTO disponibles para la asignación
    public List<BusRutaDiaDTO> obtenerBusesRutaDiaDisponibles() {
        // Implementa la lógica para obtener las rutas disponibles y convertirlas a BusRutaDiaDTO
        List<BusRutaDia> busesRutaDia = busRutaDiaRepository.findAll(); // Obtén los datos del repositorio
        return busesRutaDia.stream()
                .map(busRutaDia -> modelMapper.map(busRutaDia, BusRutaDiaDTO.class)) // Mapea a DTO
                .collect(Collectors.toList());
    }
}

