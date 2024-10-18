package com.co;

import com.co.controller.EstacionController;
import com.co.dto.EstacionDTO;
import com.co.service.EstacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstacionController.class)
public class EstacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstacionService estacionService;

    private EstacionDTO estacionDTO1;
    private EstacionDTO estacionDTO2;

    @BeforeEach
    public void setUp() {
        estacionDTO1 = new EstacionDTO(1L, "Estación Central");
        estacionDTO2 = new EstacionDTO(2L, "Estación Norte");
    }

    @Test
    public void testObtenerEstaciones() throws Exception {
        // Mock del servicio para devolver una lista de estaciones
        List<EstacionDTO> estaciones = Arrays.asList(estacionDTO1, estacionDTO2);
        when(estacionService.obtenerEstaciones()).thenReturn(estaciones);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/estaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Estación Central"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Estación Norte"));
    }
}
