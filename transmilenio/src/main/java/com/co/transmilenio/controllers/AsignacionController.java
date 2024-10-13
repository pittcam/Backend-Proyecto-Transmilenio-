package com.co.transmilenio.controllers;

import com.co.transmilenio.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @GetMapping
    public List<AsignacionDTO> listarAsignaciones() {
        return asignacionService.listarAsignaciones();
    }

    @GetMapping("/existe-por-ruta/{rutaId}")
    public boolean existeAsignacionPorRuta(@PathVariable Long rutaId) {
        return asignacionService.existeAsignacionPorRuta(rutaId);
    }

    @PostMapping
    public AsignacionDTO crearAsignacion(@RequestBody AsignacionDTO asignacionDTO) {
        return asignacionService.crearAsignacion(asignacionDTO);
    }
}