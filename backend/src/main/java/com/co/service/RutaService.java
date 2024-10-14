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
    private ModelMapper modelMapper;

    // Crear una nueva ruta
    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        Ruta ruta = modelMapper.map(rutaDTO, Ruta.class);
        ruta = rutaRepository.save(ruta);
        return modelMapper.map(ruta, RutaDTO.class);
    }

    // Actualizar una ruta existente
    public RutaDTO actualizarRuta(Long id, RutaDTO rutaDTO) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        modelMapper.map(rutaDTO, rutaExistente); // Actualiza las propiedades
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

        // Convertir estaciones a una lista de IDs
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
