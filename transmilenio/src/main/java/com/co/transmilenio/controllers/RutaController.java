package com.co.transmilenio.controllers;

import com.co.transmilenio.dto.RutaDTO;
import com.co.transmilenio.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @GetMapping
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }

    @GetMapping("/{id}")
    public RutaDTO obtenerRutaPorId(@PathVariable Long id) {
        return rutaService.obtenerRutaPorId(id);
    }

    @PostMapping
    public RutaDTO crearRuta(@RequestBody RutaDTO rutaDTO) {
        return rutaService.crearRuta(rutaDTO);
    }

    @PutMapping("/{id}")
    public RutaDTO actualizarRuta(@PathVariable Long id, @RequestBody RutaDTO rutaDTO) {
        return rutaService.actualizarRuta(id, rutaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarRuta(@PathVariable Long id) {
        rutaService.eliminarRuta(id);
    }
}
