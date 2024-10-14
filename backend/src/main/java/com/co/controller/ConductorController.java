package com.co.controller;

import com.co.dto.ConductorDTO;
import com.co.model.Conductor;
import com.co.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conductor")
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    // http://localhost:8080/conductor
    @GetMapping
    public List<Conductor> recuperarConductores()throws InterruptedException {
        Thread.sleep(2000);
        List<Conductor> conductors = conductorService.getAllConductores();
        return conductors;
    }

    // Nuevo endpoint para buscar conductores por nombre
    @GetMapping("/search")
    public ResponseEntity<List<ConductorDTO>> buscarConductoresPorNombre(@RequestParam String nombre) {
        List<ConductorDTO> conductores = conductorService
                .buscarConductoresPorNombre(nombre);
        return new ResponseEntity<>(conductores, HttpStatus.OK);
    }

    // http://localhost:8080/conductor/1
    @GetMapping("/{id}")
    public ConductorDTO recuperarConductor(@PathVariable Long id) {
        ConductorDTO conductorDTO = conductorService.getConductor(id);
        return conductorDTO;
    }

    @PostMapping
    public ConductorDTO crearConductor(@RequestBody ConductorDTO conductorDTO) {
        return conductorService.createConductor(conductorDTO);
    }

    // Idempotency
    @PutMapping("/{id}")
    public ResponseEntity<ConductorDTO> actualizarConductor(@PathVariable Long id, @RequestBody ConductorDTO conductorDTO) {
        conductorDTO.setId(id);  // Suponiendo que el DTO tiene un método setId
        ConductorDTO actualizado = conductorService.saveConductor(conductorDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar conductor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable Long id) {
        conductorService.deleteConductor(id);
        return ResponseEntity.noContent().build();
    }
}
