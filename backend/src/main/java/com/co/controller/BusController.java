package com.co.controller;

import com.co.dto.BusDTO;
import com.co.dto.ConductorDTO;
import com.co.dto.RutaDTO;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusService busService;

    // Obtener todos los buses
    @GetMapping
    public List<BusDTO> recuperarBuses() throws InterruptedException {
        return busService.getAllBuses();
    }

    // Obtener buses disponibles
    @GetMapping("/disponibles")
    public List<BusDTO> recuperarBusesDisponibles() {
        return busService.getBusesDisponibles();
    }

    // Obtener un bus por ID
    @GetMapping("/{id}")
    public BusDTO recuperarBus(@PathVariable Long id) {
        return busService.getBus(id);
    }

    // Crear un nuevo bus
    @PostMapping
    public BusDTO crearBus(@RequestBody BusDTO busDTO) {
        return busService.createBus(busDTO);
    }

    // Actualizar un bus
    @PutMapping("/{id}")
    public ResponseEntity<BusDTO> actualizarBus(@PathVariable Long id, @RequestBody BusDTO busDTO) {
        busDTO.setId(id);  // Establece el ID del bus
        BusDTO actualizadoBusDTO = busService.save(busDTO); // Usa el DTO para guardar
        return ResponseEntity.ok(actualizadoBusDTO);
    }

    // Asignar varias rutas a un bus
    @PostMapping("/{busId}/asignarRutas")
    public ResponseEntity<BusDTO> asignarRutas(
            @PathVariable Long busId,
            @RequestBody List<Long> rutaIds) {
        try {
            BusDTO updatedBus = busService.asignarRutas(busId, rutaIds);
            return ResponseEntity.ok(updatedBus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Buscar rutas por nombre
    @GetMapping("/search")
    public ResponseEntity<List<BusDTO>> buscarBusesPorPlaca(@RequestParam String placa) {
        List<BusDTO> buses = busService.buscarBusesPorPlaca(placa);
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    // Eliminar un bus
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBus(@PathVariable Long id) {
        busService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
