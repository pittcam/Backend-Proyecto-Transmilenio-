package com.co.service;

import com.co.dto.BusRutaDiaDTO;
import com.co.model.Asignacion;
import com.co.model.Bus;
import com.co.model.BusRutaDia;
import com.co.repository.BusRepository;
import com.co.repository.BusRutaDiaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class BusRutaDiaService {

    @Autowired
    private BusRutaDiaRepository busRutaDiaRepository;

    @Autowired
    private BusRepository busRepository; // Repositorio para buscar el Bus

    @Autowired
    private ModelMapper modelMapper;  // Inyectar ModelMapper

    // Método para guardar la asignación de bus, ruta y días
    public boolean guardarBusRutaDia(BusRutaDiaDTO busRutaDiaDTO) {
        try {
            // Convertir el DTO a la entidad BusRutaDia
            BusRutaDia busRutaDia = modelMapper.map(busRutaDiaDTO, BusRutaDia.class);

            // Buscar el Bus desde la base de datos
            Optional<Bus> busOpt = busRepository.findById(busRutaDiaDTO.getBus().getId());

            if (busOpt.isPresent()) {
                // Asignar el Bus encontrado al BusRutaDia
                busRutaDia.setBus(busOpt.get());
            } else {
                throw new IllegalArgumentException("Bus no encontrado");
            }

            // Guardar la entidad BusRutaDia en la base de datos
            busRutaDiaRepository.save(busRutaDia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarBusRutaDia(Long busId, Long rutaId) {
        // Suponiendo que tienes un método para encontrar por busId y rutaId
        Optional<BusRutaDia> asignacion = busRutaDiaRepository.findByBusIdAndRutaId(busId, rutaId);

        if (asignacion.isPresent()) {
            busRutaDiaRepository.delete(asignacion.get());  // Eliminar la asignación si se encuentra
            return true;
        } else {
            return false;  // No se encontró la asignación
        }
    }



}
