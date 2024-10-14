package com.co.controller;

import com.co.dto.EstacionDTO;
import com.co.service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estaciones")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    @GetMapping
    public List<EstacionDTO> obtenerEstaciones() {
        return estacionService.obtenerEstaciones();  // Devuelve todas las estaciones
    }
}
