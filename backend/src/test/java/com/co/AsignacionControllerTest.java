package com.co;

import com.co.dto.*;
import com.co.model.*;
import com.co.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-testing")
public class AsignacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private BusRutaDiaRepository busRutaDiaRepository;

    private Asignacion asignacion1;
    private Bus bus1;
    private Conductor conductor1;
    private Ruta ruta1;
    private BusRutaDia busRutaDia1;
    private Estacion estacion1;
    private Estacion estacion2;

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar los datos de prueba previos
        asignacionRepository.deleteAll();
        busRutaDiaRepository.deleteAll();
        busRepository.deleteAll();
        conductorRepository.deleteAll();
        rutaRepository.deleteAll();

        // Crear estaciones
        estacion1 = new Estacion();
        estacion1.setNombre("Estacion 1");
        estacion1 = estacionRepository.saveAndFlush(estacion1);

        estacion2 = new Estacion();
        estacion2.setNombre("Estacion 2");
        estacion2 = estacionRepository.saveAndFlush(estacion2);

        // Crear rutas
        ruta1 = new Ruta();
        ruta1.setNombre("R1");
        ruta1.setHoraInicio("05:00");
        ruta1.setHoraFin("23:00");
        ruta1.setDias(new ArrayList<>(Set.of("Lunes", "Martes")));
        ruta1 = rutaRepository.saveAndFlush(ruta1);

        // Crear buses
        bus1 = new Bus();
        bus1.setPlaca("ABC123");
        bus1.setModelo("Modelo Bus 1");
        bus1 = busRepository.saveAndFlush(bus1);

        // Crear conductores
        conductor1 = new Conductor();
        conductor1.setNombre("Juan Pérez");
        conductor1.setCedula("12345678");
        conductor1.setTelefono("3001234567");
        conductor1.setDireccion("Calle 1 # 2-3");
        conductor1 = conductorRepository.saveAndFlush(conductor1);

        // Crear asignaciones de bus a rutas y días específicos
        busRutaDia1 = new BusRutaDia();
        busRutaDia1.setRuta(ruta1);
        busRutaDia1.setDias(new ArrayList<>(Set.of("Lunes", "Martes")));
        busRutaDia1.setBus(bus1);
        busRutaDia1 = busRutaDiaRepository.saveAndFlush(busRutaDia1);

        // Crear una asignación para el conductor con el bus y días asignados
        asignacion1 = new Asignacion();
        asignacion1.setConductor(conductor1);
        asignacion1.setBusRutaDias(new ArrayList<>(List.of(busRutaDia1)));
        asignacion1 = asignacionRepository.saveAndFlush(asignacion1);
    }

    @Test
    @Transactional
    public void testGetAllAsignaciones() throws Exception {
        mockMvc.perform(get("/asignacion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].conductor.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].busRutaDias[0].bus.placa").value("ABC123"))
                .andExpect(jsonPath("$[0].busRutaDias[0].ruta.nombre").value("R1"));
    }

    @Test
    @Transactional
    public void testGetAsignacionById() throws Exception {
        mockMvc.perform(get("/asignacion/{id}", asignacion1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.conductor.nombre").value("Juan Pérez"));
    }


    @Test
    @Transactional
    public void testGetBusesPorConductor() throws Exception {
        mockMvc.perform(get("/asignacion/conductor/{conductorId}/buses", conductor1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].placa").value("ABC123"));
    }

    @Test
    @Transactional
    public void testGetBusesPorConductorInexistente() throws Exception {
        mockMvc.perform(get("/asignacion/conductor/{conductorId}/buses", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void testAsignarRutaABus() throws Exception {
        Bus newBus = new Bus();
        newBus.setPlaca("XYZ789");
        newBus.setModelo("Nuevo Modelo");
        newBus = busRepository.saveAndFlush(newBus);

        Ruta newRuta = new Ruta();
        newRuta.setNombre("R2");
        newRuta.setHoraInicio("06:00");
        newRuta.setHoraFin("22:00");
        newRuta.setDias(new ArrayList<>(Set.of("Miércoles", "Jueves")));
        newRuta = rutaRepository.saveAndFlush(newRuta);

        mockMvc.perform(post("/asignacion/asignar")
                        .param("busId", newBus.getId().toString())
                        .param("rutaId", newRuta.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testDeleteAsignacion() throws Exception {
        mockMvc.perform(delete("/asignacion/{id}", asignacion1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar que la asignación fue eliminada
        mockMvc.perform(get("/asignacion/{id}", asignacion1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @Transactional
    public void testListarBuses() throws Exception {
        mockMvc.perform(get("/asignacion/buses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].placa").value("ABC123"));
    }

    @Test
    @Transactional
    public void testListarRutas() throws Exception {
        mockMvc.perform(get("/asignacion/rutas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].nombre").value("R1"));
    }

    @Test
    @Transactional
    public void testObtenerAsignacionPorConductor() throws Exception {
        mockMvc.perform(get("/asignacion/conductor/{conductorId}", conductor1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.conductor.nombre").value("Juan Pérez"));
    }

    @Test
    @Transactional
    public void testCrearAsignacion() throws Exception {
        // Crear un nuevo conductor para esta prueba
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setNombre("Pedro García");
        nuevoConductor.setCedula("87654321");
        nuevoConductor.setTelefono("3009876543");
        nuevoConductor.setDireccion("Calle 4 # 5-6");
        nuevoConductor = conductorRepository.saveAndFlush(nuevoConductor);

        // Crear un nuevo bus para esta prueba
        Bus nuevoBus = new Bus();
        nuevoBus.setPlaca("DEF456");
        nuevoBus.setModelo("Modelo Bus 2");
        nuevoBus = busRepository.saveAndFlush(nuevoBus);

        // Crear una nueva ruta para esta prueba
        Ruta nuevaRuta = new Ruta();
        nuevaRuta.setNombre("R2");
        nuevaRuta.setHoraInicio("06:00");
        nuevaRuta.setHoraFin("22:00");
        nuevaRuta.setDias(new ArrayList<>(Set.of("Miércoles", "Jueves")));
        nuevaRuta = rutaRepository.saveAndFlush(nuevaRuta);

        // Crear el DTO del conductor
        ConductorDTO conductorDTO = new ConductorDTO();
        conductorDTO.setId(nuevoConductor.getId());
        conductorDTO.setNombre(nuevoConductor.getNombre());
        conductorDTO.setCedula(nuevoConductor.getCedula());
        conductorDTO.setTelefono(nuevoConductor.getTelefono());
        conductorDTO.setDireccion(nuevoConductor.getDireccion());

        // Crear el DTO del bus
        BusDTO busDTO = new BusDTO();
        busDTO.setId(nuevoBus.getId());
        busDTO.setPlaca(nuevoBus.getPlaca());
        busDTO.setModelo(nuevoBus.getModelo());

        // Crear el DTO de la ruta
        RutaDTO rutaDTO = new RutaDTO();
        rutaDTO.setId(nuevaRuta.getId());
        rutaDTO.setNombre(nuevaRuta.getNombre());
        rutaDTO.setHoraInicio(nuevaRuta.getHoraInicio());
        rutaDTO.setHoraFin(nuevaRuta.getHoraFin());
        rutaDTO.setDias(nuevaRuta.getDias());

        // Crear el DTO de BusRutaDia
        BusRutaDiaDTO busRutaDiaDTO = new BusRutaDiaDTO();
        busRutaDiaDTO.setBus(busDTO);
        busRutaDiaDTO.setRuta(rutaDTO);
        busRutaDiaDTO.setDias(new ArrayList<>(Set.of("Miércoles", "Jueves")));

        // Crear el DTO de Asignación
        AsignacionDTO asignacionDTO = new AsignacionDTO();
        asignacionDTO.setConductor(conductorDTO);
        asignacionDTO.setBusRutaDias(Collections.singletonList(busRutaDiaDTO));

        // Realizar la petición POST
        mockMvc.perform(post("/asignacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asignacionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.conductor.nombre").value("Pedro García"))
                .andExpect(jsonPath("$.busRutaDias[0].bus.placa").value("DEF456"))
                .andExpect(jsonPath("$.busRutaDias[0].ruta.nombre").value("R2"))
                .andExpect(jsonPath("$.busRutaDias[0].dias", containsInAnyOrder("Miércoles", "Jueves")));

        // Verificar que la asignación fue creada en la base de datos
        List<Asignacion> asignaciones = asignacionRepository.findAll();
        assertThat(asignaciones)
                .filteredOn(a -> a.getConductor().getNombre().equals("Pedro García"))
                .hasSize(1);
    }



}