package com.co.controller;

import com.co.dto.AsignacionDTO;
import com.co.dto.BusDTO;
import com.co.dto.RutaDTO;
import com.co.model.Asignacion;
import com.co.model.Bus;
import com.co.service.AsignacionService;
import com.co.service.BusService;
import com.co.service.HorarioService;
import com.co.service.RutaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/asignacion")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private BusService busService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private RutaService rutaService;

    // Listar todas las asignaciones
    @GetMapping
    public List<Asignacion> listarAsignaciones() {
        return asignacionService.obtenerTodos();
    }

    // Obtener una asignación por ID
    @GetMapping("/{id}")
    public Asignacion obtenerAsignacion(@PathVariable Long id) {
        Optional<Asignacion> asignacionOpt = asignacionService.obtenerPorId(id);
        return asignacionOpt.orElse(null); // Devuelve la asignación o null si no existe
    }

    // Crear una nueva asignación
    @PostMapping
    public Asignacion crearAsignacion(@Valid @RequestBody AsignacionDTO asignacionDTO) {
        return asignacionService.guardar(asignacionDTO);
    }

    // Endpoint para obtener los buses asignados a un conductor
    @GetMapping("/conductor/{conductorId}/buses")
    public ResponseEntity<List<BusDTO>> obtenerBusesPorConductor(@PathVariable Long conductorId) {
        List<Bus> busesAsignados = asignacionService.obtenerBusesPorConductorId(conductorId);
        if (busesAsignados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<BusDTO> busesDTO = busesAsignados.stream()
                    .map(bus -> new BusDTO(bus.getId(), bus.getNumeroPlaca(), bus.getModelo()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(busesDTO, HttpStatus.OK);
        }
}

    // Asignar ruta a un bus
    @PostMapping("/asignar")
    public Asignacion asignarRutaABus(@RequestParam Long busId, @RequestParam Long rutaId) {
        return asignacionService.asignarRutaABus(busId, rutaId);
    }

    // Actualizar una asignación existente
    /*@PutMapping("/{id}")
    public Asignacion actualizarAsignacion(@PathVariable Long id, @Valid @RequestBody AsignacionDTO asignacionDTO) {
        Optional<Asignacion> asignacionOpt = asignacionService.obtenerPorId(id);
        if (asignacionOpt.isPresent()) {
            // Aquí se puede crear un método para actualizar la asignación existente
            Asignacion asignacionExistente = asignacionOpt.get();
            asignacionExistente.setBus(busService.getBusById(asignacionDTO.getBusId()));
            asignacionExistente.setConductor(conductorService.getConductorById(asignacionDTO.getConductorId()));
            asignacionExistente.setRuta(rutaService.getRutaById(asignacionDTO.getRutaId()));
            return asignacionService.guardar(asignacionDTO);
        } else {
            return null; // Manejar el caso donde la asignación no exista
        }
    }*/

    // Eliminar una asignación
    @DeleteMapping("/{id}")
    public void eliminarAsignacion(@PathVariable Long id) {
        asignacionService.eliminar(id);
    }

    // Obtener listas de buses, horarios y rutas (para usar en el frontend)
    @GetMapping("/buses")
    public List<Bus> listarBuses() {
        return busService.getAllBuses();
    }

    @GetMapping("/horarios")
    public List<HorarioDTO> listarHorarios() {
        return horarioService.obtenerHorarios();
    }

    @GetMapping("/rutas")
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }
}
