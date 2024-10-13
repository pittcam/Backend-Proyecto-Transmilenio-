package com.co.transmilenio.service;

import com.co.transmilenio.model.Ruta;
import com.co.transmilenio.repository.AsignacionRepository;
import com.co.transmilenio.repository.RutaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final AsignacionRepository asignacionRepository;

    public RutaService(RutaRepository rutaRepository, AsignacionRepository asignacionRepository) {
        this.rutaRepository = rutaRepository;
        this.asignacionRepository = asignacionRepository;
    }

    // Convertir de entidad a DTO
    private RutaDTO convertToDTO(Ruta ruta) {
        RutaDTO dto = new RutaDTO();
        dto.setId(ruta.getId());
        dto.setNombre(ruta.getNombre());
        dto.setHoraInicio(ruta.getHoraInicio());
        dto.setHoraFin(ruta.getHoraFin());
        dto.setDias(ruta.getDias());
        dto.setEstaciones(ruta.getEstaciones().stream()
                .map(Estacion::getNombre)
                .collect(Collectors.toList()));
        return dto;
    }

    // Convertir de DTO a entidad
    private Ruta convertToEntity(RutaDTO dto) {
        Ruta ruta = new Ruta();
        ruta.setId(dto.getId());
        ruta.setNombre(dto.getNombre());
        ruta.setHoraInicio(dto.getHoraInicio());
        ruta.setHoraFin(dto.getHoraFin());
        ruta.setDias(dto.getDias());
        // Aquí debes resolver las estaciones a partir de los nombres o IDs
        // ruta.setEstaciones(resolverEstaciones(dto.getEstaciones()));
        return ruta;
    }

    // Métodos del servicio ahora usan DTOs
    public List<RutaDTO> listarRutas() {
        return rutaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RutaDTO obtenerRutaPorId(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        return convertToDTO(ruta);
    }

    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        Ruta ruta = convertToEntity(rutaDTO);
        ruta = rutaRepository.save(ruta);
        return convertToDTO(ruta);
    }

    public RutaDTO actualizarRuta(Long id, RutaDTO rutaDTO) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        rutaExistente.setNombre(rutaDTO.getNombre());
        rutaExistente.setHoraInicio(rutaDTO.getHoraInicio());
        rutaExistente.setHoraFin(rutaDTO.getHoraFin());
        rutaExistente.setDias(rutaDTO.getDias());
        // Actualización de estaciones omitida por ahora
        rutaRepository.save(rutaExistente);
        return convertToDTO(rutaExistente);
    }

    public void eliminarRuta(Long id) {
        if (asignacionRepository.existsByRutaId(id)) {
            throw new RuntimeException("No se puede eliminar la ruta porque está asignada a un bus.");
        }
        rutaRepository.deleteById(id);
    }
}