package com.co.controller;

import com.co.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public List<HorarioDTO> obtenerHorarios() {
        return horarioService.obtenerHorarios();  // Devuelve todos los horarios
    }
}
