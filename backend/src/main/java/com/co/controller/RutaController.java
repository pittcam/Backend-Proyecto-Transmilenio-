package com.co.controller;

import com.co.dto.ConductorDTO;
import com.co.dto.RutaDTO;
import com.co.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    // Listar todas las rutas
    @GetMapping
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }

    // Obtener una ruta por ID
    @GetMapping("/{id}")
    public RutaDTO obtenerRuta(@PathVariable Long id) {
        return rutaService.obtenerRuta(id);
    }

    // Crear una nueva ruta
    @PostMapping
    public RutaDTO crearRuta(@RequestBody RutaDTO rutaDTO) {
        return rutaService.crearRuta(rutaDTO);
    }

    // Actualizar una ruta existente
    @PutMapping("/{id}")
    public RutaDTO actualizarRuta(@PathVariable Long id, @RequestBody RutaDTO rutaDTO) {
        return rutaService.actualizarRuta(id, rutaDTO);
    }

    // Buscar rutas por nombre
    @GetMapping("/search")
    public ResponseEntity<List<RutaDTO>> buscarRutasPorNombre(@RequestParam String nombre) {
        List<RutaDTO> rutas = rutaService.buscarRutasPorNombre(nombre);
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    // Eliminar una ruta
    @DeleteMapping("/{id}")
    public void eliminarRuta(@PathVariable Long id) {
        rutaService.eliminarRuta(id);
    }
}
