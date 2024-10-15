package com.co.controller;

import com.co.dto.BusRutaDiaDTO;
import com.co.model.Asignacion;
import com.co.model.BusRutaDia;
import com.co.repository.BusRutaDiaRepository;
import com.co.service.AsignacionService;
import com.co.service.BusRutaDiaService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private BusRutaDiaService busRutaDiaService;

    @Autowired
    private BusRutaDiaRepository busRutaDiaRepository;


    // Obtener lista de BusRutaDiaDTO para asignación
    @GetMapping("/disponibles")
    public ResponseEntity<List<BusRutaDiaDTO>> obtenerBusesRutaDiaDisponibles() {
        List<BusRutaDiaDTO> busesRutaDia = asignacionService.obtenerBusesRutaDiaDisponibles();
        if (busesRutaDia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(busesRutaDia, HttpStatus.OK);
    }

    // Metodo para guardar los días seleccionados para una ruta de bus
    @PostMapping("/guardar-dias")
    public ResponseEntity<String> guardarDias(@RequestBody BusRutaDiaDTO busRutaDiaDTO) {
        boolean guardado = busRutaDiaService.guardarBusRutaDia(busRutaDiaDTO);
        if (guardado) {
            return new ResponseEntity<>("Días guardados exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al guardar los días", HttpStatus.BAD_REQUEST);
        }
    }





}
