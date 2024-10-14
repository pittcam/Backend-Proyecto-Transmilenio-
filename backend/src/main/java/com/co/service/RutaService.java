package com.co.service;

import com.co.dto.RutaDTO;
import com.co.model.Ruta;
import com.co.model.Estacion;
import com.co.repository.RutaRepository;
import com.co.repository.EstacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crear una nueva ruta
    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        // Crear una nueva instancia de Ruta
        Ruta ruta = new Ruta();
        ruta.setNombre(rutaDTO.getNombre());
        ruta.setHoraInicio(rutaDTO.getHoraInicio());
        ruta.setHoraFin(rutaDTO.getHoraFin());
        ruta.setDias(rutaDTO.getDias());

        // Convertir los IDs de las estaciones a entidades
        List<Estacion> estaciones = rutaDTO.getEstacionesIds().stream()
                .map(estacionId -> estacionRepository.findById(estacionId)
                        .orElseThrow(() -> new RuntimeException("Estación no encontrada: " + estacionId)))
                .collect(Collectors.toList());

        ruta.setEstaciones(estaciones);  // Asignar las estaciones a la ruta

        // Guardar la ruta con las estaciones asociadas
        Ruta rutaGuardada = rutaRepository.save(ruta);

        // Retornar el DTO mapeado
        return modelMapper.map(rutaGuardada, RutaDTO.class);
    }


    // Actualizar una ruta existente
    public RutaDTO actualizarRuta(Long id, RutaDTO rutaDTO) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        // Actualizar todas las propiedades
        rutaExistente.setNombre(rutaDTO.getNombre());
        rutaExistente.setHoraInicio(rutaDTO.getHoraInicio());
        rutaExistente.setHoraFin(rutaDTO.getHoraFin());
        rutaExistente.setDias(rutaDTO.getDias());

        // Convertir los IDs a entidades Estacion
        List<Estacion> estaciones = rutaDTO.getEstacionesIds().stream()
                .map(estacionId -> estacionRepository.findById(estacionId)
                        .orElseThrow(() -> new RuntimeException("Estación no encontrada: " + estacionId)))
                .collect(Collectors.toList());

        rutaExistente.setEstaciones(estaciones);

        // Guardar la ruta actualizada
        rutaExistente = rutaRepository.save(rutaExistente);

        return modelMapper.map(rutaExistente, RutaDTO.class);
    }

    // Listar todas las rutas
    public List<RutaDTO> listarRutas() {
        return rutaRepository.findAll().stream()
                .map(ruta -> modelMapper.map(ruta, RutaDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar rutas por nombre
    public List<RutaDTO> buscarRutasPorNombre(String nombre) {
        List<Ruta> rutas = rutaRepository.findAllByNombreContainingIgnoreCase(nombre);
        return rutas.stream()
                .map(ruta -> modelMapper.map(ruta, RutaDTO.class))
                .collect(Collectors.toList());
    }

    // Eliminar una ruta
    public void eliminarRuta(Long id) {
        rutaRepository.deleteById(id);
    }

    // Obtener una ruta por ID
    public RutaDTO obtenerRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        List<Long> estacionesIds = ruta.getEstaciones().stream()
                .map(Estacion::getId)
                .collect(Collectors.toList());

        return new RutaDTO(
                ruta.getId(),
                ruta.getNombre(),
                estacionesIds,
                ruta.getHoraInicio(),
                ruta.getHoraFin(),
                ruta.getDias()
        );
    }
}
