package com.co.service;

import com.co.conversion.RutaDTOConverter;
import com.co.dto.RutaDTO;
import com.co.model.Ruta;
import com.co.repository.RutaRepository;
import com.co.repository.EstacionRepository;
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
    private RutaDTOConverter rutaDTOConverter;

    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        // Obtener las estaciones a partir de los IDs
        Set<Estacion> estaciones = estacionRepository.findAllById(rutaDTO.getEstacionesIds())
                .stream()
                .collect(Collectors.toSet());

        // Crear la nueva ruta
        Ruta nuevaRuta = rutaDTOConverter.dtoToEntity(rutaDTO, estaciones);

        // Agregar los IDs de horarios a la nueva ruta
        nuevaRuta.setHorarioFuncionamiento(rutaDTO.getHorarioFuncionamiento());

        // Guardar la nueva ruta en la base de datos
        rutaRepository.save(nuevaRuta);

        return rutaDTOConverter.entityToDTO(nuevaRuta);
    }


    public RutaDTO actualizarRuta(Long id, RutaDTO rutaDTO) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        Set<Estacion> estaciones = estacionRepository.findAllById(rutaDTO.getEstacionesIds())
                .stream()
                .collect(Collectors.toSet());

        rutaExistente.setNombre(rutaDTO.getNombre());
        rutaExistente.setEstaciones(estaciones);
        rutaExistente.setHorarioFuncionamiento(rutaDTO.getHorarioFuncionamiento());

        rutaRepository.save(rutaExistente);
        return rutaDTOConverter.entityToDTO(rutaExistente);
    }

    public void eliminarRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        if (!ruta.getAsignaciones().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la ruta porque tiene buses asignados");
        }
        rutaRepository.deleteById(id);
    }

    public RutaDTO obtenerRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id).orElse(null);
        if (ruta != null) {
            // Aquí puedes cargar otros detalles si es necesario
            return new RutaDTO(ruta.getId(), ruta.getNombre(),
                    // Convierte las estaciones a IDs
                    ruta.getEstaciones().stream().map(Estacion::getId).collect(Collectors.toSet()),
                    ruta.getHorarioFuncionamiento());
        }
        return null; // O lanza una excepción
    }


    public List<RutaDTO> listarRutas() {
        return rutaRepository.findAll().stream()
                .map(rutaDTOConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    // Buscar ruta por nombre
    public List<RutaDTO> buscarRutasPorNombre(String nombre) {
        List<Ruta> rutas = rutaRepository.findAllByNombreContainingIgnoreCase(nombre);
        return rutaDTOConverter.entitiesToDTOs(rutas);
    }
}
