package com.co;

import com.co.controller.RutaController;
import com.co.dto.RutaDTO;
import com.co.service.RutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RutaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RutaService rutaService;

    @InjectMocks
    private RutaController rutaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rutaController).build();
    }

    @Test
    public void testListarRutas() throws Exception {
        // Simular que el servicio devuelve una lista vacía
        when(rutaService.listarRutas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rutas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testObtenerRuta() throws Exception {
        // Simular una ruta específica
        RutaDTO rutaDTO = new RutaDTO(1L, "Ruta 1", Arrays.asList(1L, 2L), "06:00", "18:00", Arrays.asList("Lunes", "Martes"));
        when(rutaService.obtenerRuta(1L)).thenReturn(rutaDTO);

        mockMvc.perform(get("/rutas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Ruta 1"))
                .andExpect(jsonPath("$.estacionesIds").isArray())
                .andExpect(jsonPath("$.estacionesIds[0]").value(1L))
                .andExpect(jsonPath("$.estacionesIds[1]").value(2L))
                .andExpect(jsonPath("$.horaInicio").value("06:00"))
                .andExpect(jsonPath("$.horaFin").value("18:00"))
                .andExpect(jsonPath("$.dias").isArray())
                .andExpect(jsonPath("$.dias[0]").value("Lunes"))
                .andExpect(jsonPath("$.dias[1]").value("Martes"));
    }

    @Test
    public void testCrearRuta() throws Exception {
        // Simular una creación exitosa
        RutaDTO rutaDTO = new RutaDTO(null, "Nueva Ruta", Arrays.asList(1L, 2L), "05:00", "20:00", Arrays.asList("Miércoles", "Jueves"));
        RutaDTO rutaCreada = new RutaDTO(1L, "Nueva Ruta", Arrays.asList(1L, 2L), "05:00", "20:00", Arrays.asList("Miércoles", "Jueves"));

        when(rutaService.crearRuta(any(RutaDTO.class))).thenReturn(rutaCreada);

        mockMvc.perform(post("/rutas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Nueva Ruta\", \"estacionesIds\": [1, 2], \"horaInicio\": \"05:00\", \"horaFin\": \"20:00\", \"dias\": [\"Miércoles\", \"Jueves\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Nueva Ruta"))
                .andExpect(jsonPath("$.estacionesIds").isArray())
                .andExpect(jsonPath("$.estacionesIds[0]").value(1L))
                .andExpect(jsonPath("$.estacionesIds[1]").value(2L))
                .andExpect(jsonPath("$.horaInicio").value("05:00"))
                .andExpect(jsonPath("$.horaFin").value("20:00"))
                .andExpect(jsonPath("$.dias").isArray())
                .andExpect(jsonPath("$.dias[0]").value("Miércoles"))
                .andExpect(jsonPath("$.dias[1]").value("Jueves"));
    }

    @Test
    public void testActualizarRuta() throws Exception {
        // Simular una actualización exitosa
        RutaDTO rutaDTO = new RutaDTO(1L, "Ruta Actualizada", Arrays.asList(3L, 4L), "07:00", "19:00", Arrays.asList("Viernes", "Sábado"));

        when(rutaService.actualizarRuta(eq(1L), any(RutaDTO.class))).thenReturn(rutaDTO);

        mockMvc.perform(put("/rutas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Ruta Actualizada\", \"estacionesIds\": [3, 4], \"horaInicio\": \"07:00\", \"horaFin\": \"19:00\", \"dias\": [\"Viernes\", \"Sábado\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Ruta Actualizada"))
                .andExpect(jsonPath("$.estacionesIds").isArray())
                .andExpect(jsonPath("$.estacionesIds[0]").value(3L))
                .andExpect(jsonPath("$.estacionesIds[1]").value(4L))
                .andExpect(jsonPath("$.horaInicio").value("07:00"))
                .andExpect(jsonPath("$.horaFin").value("19:00"))
                .andExpect(jsonPath("$.dias").isArray())
                .andExpect(jsonPath("$.dias[0]").value("Viernes"))
                .andExpect(jsonPath("$.dias[1]").value("Sábado"));
    }

    @Test
    public void testBuscarRutasPorNombre() throws Exception {
        // Simular una búsqueda por nombre
        List<RutaDTO> rutas = Arrays.asList(
                new RutaDTO(1L, "Ruta 1", Arrays.asList(1L, 2L), "06:00", "18:00", Arrays.asList("Lunes", "Martes")),
                new RutaDTO(2L, "Ruta 2", Arrays.asList(2L, 3L), "07:00", "19:00", Arrays.asList("Miércoles", "Jueves"))
        );
        when(rutaService.buscarRutasPorNombre("Ruta")).thenReturn(rutas);

        mockMvc.perform(get("/rutas/search?nombre=Ruta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Ruta 1"))
                .andExpect(jsonPath("$[0].estacionesIds").isArray())
                .andExpect(jsonPath("$[0].estacionesIds[0]").value(1L))
                .andExpect(jsonPath("$[0].estacionesIds[1]").value(2L))
                .andExpect(jsonPath("$[0].horaInicio").value("06:00"))
                .andExpect(jsonPath("$[0].horaFin").value("18:00"))
                .andExpect(jsonPath("$[0].dias").isArray())
                .andExpect(jsonPath("$[0].dias[0]").value("Lunes"))
                .andExpect(jsonPath("$[0].dias[1]").value("Martes"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("Ruta 2"))
                .andExpect(jsonPath("$[1].estacionesIds").isArray())
                .andExpect(jsonPath("$[1].estacionesIds[0]").value(2L))
                .andExpect(jsonPath("$[1].estacionesIds[1]").value(3L))
                .andExpect(jsonPath("$[1].horaInicio").value("07:00"))
                .andExpect(jsonPath("$[1].horaFin").value("19:00"))
                .andExpect(jsonPath("$[1].dias").isArray())
                .andExpect(jsonPath("$[1].dias[0]").value("Miércoles"))
                .andExpect(jsonPath("$[1].dias[1]").value("Jueves"));
    }

    @Test
    public void testEliminarRuta() throws Exception {
        // No se necesita simulación para delete
        doNothing().when(rutaService).eliminarRuta(1L);

        mockMvc.perform(delete("/rutas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar que el método eliminarRuta fue llamado una vez con el ID correcto
        verify(rutaService, times(1)).eliminarRuta(1L);
    }
}
