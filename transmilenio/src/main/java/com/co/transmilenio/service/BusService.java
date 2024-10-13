package com.co.transmilenio.service;

import com.co.transmilenio.dto.BusDTO;
import com.co.transmilenio.dto.BusRutaDiaDTO;
import com.co.transmilenio.model.Bus;
import com.co.transmilenio.model.Asignacion;
import com.co.transmilenio.model.BusRutaDia;
import com.co.transmilenio.model.Ruta;
import com.co.transmilenio.repository.BusRepository;
import com.co.transmilenio.repository.AsignacionRepository;
import com.co.transmilenio.repository.RutaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BusDTO> listarBuses() {
        return busRepository.findAll().stream()
                .map(bus -> modelMapper.map(bus, BusDTO.class))
                .collect(Collectors.toList());
    }

    public BusDTO obtenerBusPorId(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
        return modelMapper.map(bus, BusDTO.class);
    }

    public void validarRutasNoCruzadasEntreBuses(List<Bus> buses) {
        List<BusRutaDia> todasLasRutasDias = buses.stream()
                .flatMap(bus -> bus.getRutasDias().stream())
                .collect(Collectors.toList());

        for (int i = 0; i < todasLasRutasDias.size(); i++) {
            for (int j = i + 1; j < todasLasRutasDias.size(); j++) {
                BusRutaDia rutaDia1 = todasLasRutasDias.get(i);
                BusRutaDia rutaDia2 = todasLasRutasDias.get(j);

                if (rutasSeCruzaron(
                        rutaDia1.getRuta(), rutaDia2.getRuta(),
                        rutaDia1.getDias(), rutaDia2.getDias())) {
                    throw new RuntimeException("Los buses tienen rutas con conflictos de horario.");
                }
            }
        }
    }

    public BusDTO crearBus(BusDTO busDTO) {
        // Validación de placa única
        if (busRepository.existsByPlaca(busDTO.getPlaca())) {
            throw new RuntimeException("Ya existe un bus con esta placa");
        }

        // Crear el bus
        Bus bus = modelMapper.map(busDTO, Bus.class);

        // Validar compatibilidad de rutas
        validarCompatibilidadRutas(busDTO.getRutasDias());

        // Guardar el bus con las rutas y días asignados
        bus.setRutasDias(mapToEntity(busDTO.getRutasDias(), bus));
        bus = busRepository.save(bus);

        return modelMapper.map(bus, BusDTO.class);
    }

    private void validarCompatibilidadRutas(List<BusRutaDiaDTO> rutasDias) {
        for (int i = 0; i < rutasDias.size(); i++) {
            for (int j = i + 1; j < rutasDias.size(); j++) {
                BusRutaDiaDTO dia1 = rutasDias.get(i);
                BusRutaDiaDTO dia2 = rutasDias.get(j);

                Ruta ruta1 = rutaRepository.findById(dia1.getRutaId())
                        .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
                Ruta ruta2 = rutaRepository.findById(dia2.getRutaId())
                        .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

                if (rutasSeCruzaron(ruta1, ruta2, dia1.getDias(), dia2.getDias())) {
                    throw new RuntimeException("Las rutas seleccionadas tienen conflictos de horario.");
                }
            }
        }
    }

    public BusDTO actualizarBus(Long id, BusDTO busDTO) {
        Bus busExistente = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));

        // Actualizamos el bus con el nuevo DTO
        modelMapper.map(busDTO, busExistente);

        // Validación de rutas sin cruce usando la lista de rutas asignadas
        validarRutasNoCruzadas(busDTO);

        busExistente.setRutasDias(mapToEntity(busDTO.getRutasDias(), busExistente));
        busRepository.save(busExistente);

        return modelMapper.map(busExistente, BusDTO.class);
    }


    public void eliminarBus(Long id) {
        List<Asignacion> asignaciones = asignacionRepository.findByBusesId(id);
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getConductor() != null) {
                throw new RuntimeException("No se puede eliminar el bus, está asignado a un conductor.");
            }
        }
        busRepository.deleteById(id);
    }

    private void validarRutasNoCruzadas(BusDTO busDTO) {
        List<Ruta> rutas = busDTO.getRutasDias().stream()
                .map(rutaDiaDTO -> rutaRepository.findById(rutaDiaDTO.getRutaId())
                        .orElseThrow(() -> new RuntimeException("Ruta no encontrada")))
                .collect(Collectors.toList());

        for (int i = 0; i < rutas.size(); i++) {
            for (int j = i + 1; j < rutas.size(); j++) {
                if (rutasSeCruzaron(rutas.get(i), rutas.get(j))) {
                    throw new RuntimeException("Las rutas seleccionadas tienen conflictos de horario.");
                }
            }
        }
    }

    public boolean rutasSeCruzaron(Ruta ruta1, Ruta ruta2) {
        Set<Character> diasComunes = new HashSet<>(ruta1.getDias());
        diasComunes.retainAll(ruta2.getDias());

        if (!diasComunes.isEmpty()) {
            // Validar si los horarios se cruzan en los días comunes
            return !(ruta1.getHoraFin().compareTo(ruta2.getHoraInicio()) <= 0 ||
                    ruta2.getHoraFin().compareTo(ruta1.getHoraInicio()) <= 0);
        }
        return false;
    }


    public boolean rutasSeCruzaron(Ruta ruta1, Ruta ruta2, Set<Character> dias1, Set<Character> dias2) {
        Set<Character> diasComunes = new HashSet<>(dias1);
        diasComunes.retainAll(dias2);

        if (!diasComunes.isEmpty()) {
            // Validar si los horarios se cruzan en los días comunes
            return !(ruta1.getHoraFin().compareTo(ruta2.getHoraInicio()) <= 0 ||
                    ruta2.getHoraFin().compareTo(ruta1.getHoraInicio()) <= 0);
        }
        return false;
    }

    private List<BusRutaDia> mapToEntity(List<BusRutaDiaDTO> rutasDiasDTO, Bus bus) {
        return rutasDiasDTO.stream().map(dto -> {
            Ruta ruta = rutaRepository.findById(dto.getRutaId())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

            return new BusRutaDia(null, bus, ruta, dto.getDias());
        }).collect(Collectors.toList());
    }
}
