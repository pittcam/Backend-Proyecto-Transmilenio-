package com.co.transmilenio.service;

import com.co.transmilenio.dto.RutaDTO;
import com.co.transmilenio.model.Ruta;
import com.co.transmilenio.repository.AsignacionRepository;
import com.co.transmilenio.repository.RutaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private ModelMapper modelMapper;  // Inyectamos el ModelMapper

    public List<RutaDTO> listarRutas() {
        return rutaRepository.findAll().stream()
                .map(ruta -> modelMapper.map(ruta, RutaDTO.class))  // Convertimos de entidad a DTO
                .collect(Collectors.toList());
    }

    public RutaDTO obtenerRutaPorId(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        return modelMapper.map(ruta, RutaDTO.class);  // Convertimos a DTO
    }

    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        Ruta ruta = modelMapper.map(rutaDTO, Ruta.class);  // Convertimos de DTO a entidad
        ruta = rutaRepository.save(ruta);
        return modelMapper.map(ruta, RutaDTO.class);  // Convertimos de vuelta a DTO
    }

    public RutaDTO actualizarRuta(Long id, RutaDTO rutaDTO) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        modelMapper.map(rutaDTO, rutaExistente);  // Actualizamos los campos de la entidad
        rutaRepository.save(rutaExistente);
        return modelMapper.map(rutaExistente, RutaDTO.class);  // Convertimos a DTO
    }

    public void eliminarRuta(Long id) {
        if (asignacionRepository.existsByRutaId(id)) {
            throw new RuntimeException("No se puede eliminar la ruta porque está asignada a un bus.");
        }
        rutaRepository.deleteById(id);
    }
}
