package com.co.controller;

import com.co.dto.AsignacionDTO;
import com.co.dto.BusDTO;
import com.co.dto.RutaDTO;
import com.co.model.Asignacion;
import com.co.model.Bus;
import com.co.service.AsignacionService;
import com.co.service.BusService;
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
    private RutaService rutaService;

    // Listar todas las asignaciones
    @GetMapping
    public List<Asignacion> listarAsignaciones() {
        return asignacionService.obtenerTodos();
    }

    // Obtener una asignación por ID
    @GetMapping("/{id}")
    public Asignacion obtenerAsignacion(@PathVariable Long id) {
        return asignacionService.obtenerPorId(id).orElse(null); // Devuelve la asignación o null si no existe
    }

    // Crear una nueva asignación
    @PostMapping
    public Asignacion crearAsignacion(@Valid @RequestBody AsignacionDTO asignacionDTO) {
        return asignacionService.guardar(asignacionDTO);
    }

    // Endpoint para obtener los buses asignados a un conductor
    @GetMapping("/conductor/{conductorId}/buses")
    public ResponseEntity<List<BusDTO>> obtenerBusesPorConductor(@PathVariable Long conductorId) {
        List<BusDTO> busesDTO = asignacionService.obtenerBusesPorConductorId(conductorId);
        if (busesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(busesDTO, HttpStatus.OK);
        }
    }

    // Asignar ruta a un bus
    @PostMapping("/asignar")
    public Asignacion asignarRutaABus(@RequestParam Long busId, @RequestParam Long rutaId) {
        return asignacionService.asignarRutaABus(busId, rutaId);
    }

    // Eliminar una asignación
    @DeleteMapping("/{id}")
    public void eliminarAsignacion(@PathVariable Long id) {
        asignacionService.eliminar(id);
    }

    // Obtener listas de buses
    @GetMapping("/buses")
    public List<BusDTO> listarBuses() {
        return busService.getAllBuses();
    }

    // Obtener listas de rutas
    @GetMapping("/rutas")
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }
}
