package com.co;

import com.co.controller.BusController;
import com.co.dto.BusDTO;
import com.co.service.BusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BusControllerTest {

    @InjectMocks
    private BusController busController;

    @Mock
    private BusService busService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecuperarBuses() throws InterruptedException {
        // Prepara datos de prueba
        BusDTO bus1 = new BusDTO(1L, "ABC123", "Modelo X", null);
        BusDTO bus2 = new BusDTO(2L, "XYZ456", "Modelo Y", null);
        List<BusDTO> buses = Arrays.asList(bus1, bus2);

        // Simula la respuesta del servicio
        when(busService.getAllBuses()).thenReturn(buses);

        // Llama al método del controlador
        List<BusDTO> resultado = busController.recuperarBuses();

        // Verifica los resultados
        assertEquals(2, resultado.size());
        assertEquals("ABC123", resultado.get(0).getPlaca());
        assertEquals("XYZ456", resultado.get(1).getPlaca());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).getAllBuses();
    }

    @Test
    public void testRecuperarBusesDisponibles() {
        // Prepara datos de prueba
        BusDTO bus1 = new BusDTO(1L, "ABC123", "Modelo X", null);
        List<BusDTO> busesDisponibles = Collections.singletonList(bus1);

        // Simula la respuesta del servicio
        when(busService.getBusesDisponibles()).thenReturn(busesDisponibles);

        // Llama al método del controlador
        List<BusDTO> resultado = busController.recuperarBusesDisponibles();

        // Verifica los resultados
        assertEquals(1, resultado.size());
        assertEquals("ABC123", resultado.get(0).getPlaca());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).getBusesDisponibles();
    }

    @Test
    public void testRecuperarBus() {
        // Prepara datos de prueba
        BusDTO bus = new BusDTO(1L, "ABC123", "Modelo X", null);

        // Simula la respuesta del servicio
        when(busService.getBus(1L)).thenReturn(bus);

        // Llama al método del controlador
        BusDTO resultado = busController.recuperarBus(1L);

        // Verifica los resultados
        assertEquals("ABC123", resultado.getPlaca());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).getBus(1L);
    }

    @Test
    public void testCrearBus() {
        // Prepara datos de prueba
        BusDTO busDTO = new BusDTO(null, "ABC123", "Modelo X", null);
        BusDTO busCreado = new BusDTO(1L, "ABC123", "Modelo X", null);

        // Simula la respuesta del servicio
        when(busService.createBus(busDTO)).thenReturn(busCreado);

        // Llama al método del controlador
        BusDTO resultado = busController.crearBus(busDTO);

        // Verifica los resultados
        assertEquals(1L, resultado.getId());
        assertEquals("ABC123", resultado.getPlaca());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).createBus(busDTO);
    }

    @Test
    public void testActualizarBus() {
        // Prepara datos de prueba
        BusDTO busDTO = new BusDTO(1L, "ABC123", "Modelo X", null);
        BusDTO busActualizado = new BusDTO(1L, "ABC123", "Modelo Y", null);

        // Simula la respuesta del servicio
        when(busService.save(busDTO)).thenReturn(busActualizado);

        // Llama al método del controlador
        ResponseEntity<BusDTO> resultado = busController.actualizarBus(1L, busDTO);

        // Verifica los resultados
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(busActualizado, resultado.getBody());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).save(busDTO);
    }

    @Test
    public void testAsignarRutas() {
        // Prepara datos de prueba
        Long busId = 1L;
        List<Long> rutaIds = Arrays.asList(1L, 2L);
        BusDTO busActualizado = new BusDTO(1L, "ABC123", "Modelo X", null);

        // Simula la respuesta del servicio
        when(busService.asignarRutas(busId, rutaIds)).thenReturn(busActualizado);

        // Llama al método del controlador
        ResponseEntity<BusDTO> resultado = busController.asignarRutas(busId, rutaIds);

        // Verifica los resultados
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(busActualizado, resultado.getBody());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).asignarRutas(busId, rutaIds);
    }

    @Test
    public void testBuscarBusesPorPlaca() {
        // Prepara datos de prueba
        BusDTO bus1 = new BusDTO(1L, "ABC123", "Modelo X", null);
        List<BusDTO> buses = Collections.singletonList(bus1);

        // Simula la respuesta del servicio
        when(busService.buscarBusesPorPlaca("ABC123")).thenReturn(buses);

        // Llama al método del controlador
        ResponseEntity<List<BusDTO>> resultado = busController.buscarBusesPorPlaca("ABC123");

        // Verifica los resultados
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        assertEquals("ABC123", resultado.getBody().get(0).getPlaca());

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).buscarBusesPorPlaca("ABC123");
    }

    @Test
    public void testEliminarBus() {
        // Prepara el ID del bus a eliminar
        Long busId = 1L;

        // Llama al método del controlador
        ResponseEntity<Void> resultado = busController.eliminarBus(busId);

        // Verifica que el servicio fue llamado
        verify(busService, times(1)).delete(busId);

        // Verifica que se devuelva el estado no content
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
    }
}
