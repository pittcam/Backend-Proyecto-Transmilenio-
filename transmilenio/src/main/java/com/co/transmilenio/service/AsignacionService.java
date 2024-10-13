package com.co.transmilenio.service;

import com.co.transmilenio.dto.AsignacionDTO;
import com.co.transmilenio.model.Asignacion;
import com.co.transmilenio.model.Bus;
import com.co.transmilenio.model.BusRutaDia;
import com.co.transmilenio.model.Ruta;
import com.co.transmilenio.repository.AsignacionRepository;
import com.co.transmilenio.repository.BusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BusService busService;  // Inyección de BusService

    public List<AsignacionDTO> listarAsignaciones() {
        return asignacionRepository.findAll().stream()
                .map(asignacion -> modelMapper.map(asignacion, AsignacionDTO.class))
                .collect(Collectors.toList());
    }

    public AsignacionDTO crearAsignacion(AsignacionDTO asignacionDTO) {
        List<Bus> buses = asignacionDTO.getBusesIds().stream()
                .map(busId -> busRepository.findById(busId)
                        .orElseThrow(() -> new RuntimeException("Bus no encontrado")))
                .collect(Collectors.toList());

        validarRutasNoCruzadasEntreBuses(buses);

        Asignacion asignacion = modelMapper.map(asignacionDTO, Asignacion.class);
        asignacion.setBuses(buses);
        asignacion = asignacionRepository.save(asignacion);

        return modelMapper.map(asignacion, AsignacionDTO.class);
    }

    private void validarRutasNoCruzadasEntreBuses(List<Bus> buses) {
        List<BusRutaDia> todasLasRutasDias = buses.stream()
                .flatMap(bus -> bus.getRutasDias().stream())
                .toList();

        for (int i = 0; i < todasLasRutasDias.size(); i++) {
            for (int j = i + 1; j < todasLasRutasDias.size(); j++) {
                BusRutaDia rutaDia1 = todasLasRutasDias.get(i);
                BusRutaDia rutaDia2 = todasLasRutasDias.get(j);

                if (busService.rutasSeCruzaron(
                        rutaDia1.getRuta(), rutaDia2.getRuta(),
                        rutaDia1.getDias(), rutaDia2.getDias())) {
                    throw new RuntimeException("Los buses tienen rutas con conflictos de horario.");
                }
            }
        }
    }

    public void eliminarAsignacionesPorConductorId(Long conductorId) {
        List<Asignacion> asignaciones = asignacionRepository.findByConductorId(conductorId);

        if (!asignaciones.isEmpty()) {
            asignacionRepository.deleteAll(asignaciones);
        }
    }
}
