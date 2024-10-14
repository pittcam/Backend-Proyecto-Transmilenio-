package com.co.service;

import com.co.dto.AsignacionDTO;
import com.co.dto.RutaDTO;
import com.co.model.Asignacion;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.model.Ruta;
import com.co.repository.AsignacionRepository;
import com.co.repository.BusRepository;
import com.co.repository.ConductorRepository;
import com.co.repository.RutaRepository;
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
    private ConductorRepository conductorRepository;

    @Autowired
    private RutaRepository rutaRepository;

    // Obtener todas las asignaciones
    public List<Asignacion> obtenerTodos() {
        return asignacionRepository.findAll();
    }

    // Obtener una asignación por ID
    public Optional<Asignacion> obtenerPorId(Long id) {
        return asignacionRepository.findById(id);
    }

    // Guardar una nueva asignación a partir de AsignacionDTO
    // Guardar una nueva asignación a partir de AsignacionDTO
    public Asignacion guardar(AsignacionDTO asignacionDTO) {
        // Validar que el bus y el conductor existan
        Bus bus = busRepository.findById(asignacionDTO.getBusId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus no válido"));

        Conductor conductor = conductorRepository.findById(asignacionDTO.getConductorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conductor no válido"));

        // Validar la ruta solo si está presente
        Ruta ruta = null;
        if (asignacionDTO.getRutaId() != null) {
            ruta = rutaRepository.findById(asignacionDTO.getRutaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ruta no válida"));
        }

        // Crear y guardar la asignación
        Asignacion asignacion = new Asignacion();
        asignacion.setBus(bus);
        asignacion.setConductor(conductor);
        if (ruta != null) {
            asignacion.setRuta(ruta);
        }

        return asignacionRepository.save(asignacion);
    }

    // Obtener los buses asignados a un conductor
    public List<Bus> obtenerBusesPorConductorId(Long conductorId) {
        List<Asignacion> asignaciones = asignacionRepository.findByConductorId(conductorId);
        return asignaciones.stream().map(Asignacion::getBus).collect(Collectors.toList());
    }



    // Asignar ruta a un bus usando DTO
    public Asignacion asignarRutaABus(Long busId, Long rutaId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus no encontrado"));

        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada"));

        Asignacion asignacion = new Asignacion();
        asignacion.setBus(bus);
        asignacion.setRuta(ruta);

        return asignacionRepository.save(asignacion);
    }

    // Eliminar una asignación
    public void eliminar(Long id) {
        asignacionRepository.deleteById(id);
    }

    // Obtener asignaciones por ID del conductor
    public List<Asignacion> findByConductorId(Long conductorId) {
        return asignacionRepository.findByConductorId(conductorId);
    }

    public List<Asignacion> findByBus(Bus bus) {
        return asignacionRepository.findByBus(bus);
    }

    public void deleteByConductorId(Long conductorId) {
        List<Asignacion> asignaciones = asignacionRepository.findByConductorId(conductorId);
        asignacionRepository.deleteAll(asignaciones);
    }
}
