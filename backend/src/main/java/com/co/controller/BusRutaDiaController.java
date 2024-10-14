package com.co.controller;

import com.co.dto.BusRutaDiaDTO;
import com.co.model.Asignacion;
import com.co.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus-ruta-dia")
public class BusRutaDiaController {

    @Autowired
    private AsignacionService asignacionService;

    // Obtener lista de BusRutaDiaDTO para asignación
    @GetMapping("/disponibles")
    public ResponseEntity<List<BusRutaDiaDTO>> obtenerBusesRutaDiaDisponibles() {
        List<BusRutaDiaDTO> busesRutaDia = asignacionService.obtenerBusesRutaDiaDisponibles();
        if (busesRutaDia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(busesRutaDia, HttpStatus.OK);
    }

}
