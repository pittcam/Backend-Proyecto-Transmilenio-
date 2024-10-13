package com.co.transmilenio.controllers;

import com.co.transmilenio.dto.BusDTO;
import com.co.transmilenio.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public List<BusDTO> listarBuses() {
        return busService.listarBuses();
    }

    @GetMapping("/{id}")
    public BusDTO obtenerBusPorId(@PathVariable Long id) {
        return busService.obtenerBusPorId(id);
    }

    @PostMapping
    public BusDTO crearBus(@RequestBody BusDTO busDTO) {
        return busService.crearBus(busDTO);
    }

    @PutMapping("/{id}")
    public BusDTO actualizarBus(@PathVariable Long id, @RequestBody BusDTO busDTO) {
        return busService.actualizarBus(id, busDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarBus(@PathVariable Long id) {
        busService.eliminarBus(id);
    }
}
