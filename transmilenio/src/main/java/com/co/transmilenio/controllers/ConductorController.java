package com.co.transmilenio.controllers;

import com.co.transmilenio.dto.ConductorDTO;
import com.co.transmilenio.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping
    public List<ConductorDTO> listarConductores() {
        return conductorService.listarConductores();
    }

    @GetMapping("/{id}")
    public ConductorDTO obtenerConductorPorId(@PathVariable Long id) {
        return conductorService.obtenerConductorPorId(id);
    }

    @PostMapping
    public ConductorDTO crearConductor(@RequestBody ConductorDTO conductorDTO,
                                       @RequestParam List<Long> busesIds) {
        return conductorService.crearConductor(conductorDTO, busesIds);
    }

    @DeleteMapping("/{id}")
    public void eliminarConductor(@PathVariable Long id) {
        conductorService.eliminarConductor(id);
    }
}

