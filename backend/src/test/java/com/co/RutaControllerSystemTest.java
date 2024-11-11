package com.co;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.co.model.Estacion;
import com.co.repository.EstacionRepository;
import com.co.model.Ruta;
import com.co.repository.RutaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RutaControllerSystemTest {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;  // Asegúrate de que esta línea esté presente y correctamente inyectada

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    private String baseUrl = "http://localhost:4200";

    @BeforeEach
    void init() {
        // Crear estaciones
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Estacion A");
        estacion1 = estacionRepository.saveAndFlush(estacion1);  // Guarda la estación en la base de datos

        Estacion estacion2 = new Estacion();
        estacion2.setNombre("Estacion B");
        estacion2 = estacionRepository.saveAndFlush(estacion2);  // Guarda la estación en la base de datos

        // Crear una sola ruta (Ruta 1) y asociarla con las estaciones
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("Ruta 1");
        ruta1.setHoraInicio("08:00");
        ruta1.setHoraFin("10:00");
        ruta1.setEstaciones(List.of(estacion1, estacion2)); // Asignando las estaciones a la ruta
        ruta1.setDias(List.of("Lunes", "Martes"));
        ruta1 = rutaRepository.saveAndFlush(ruta1);

        // Configuración de Playwright
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.browserContext = browser.newContext();
        this.page = browserContext.newPage();
    }

    @AfterEach
    void end() {
        browser.close();
        playwright.close();
    }

    @Test
    void obtenerRutasCorrecto() {
        // Navegar a la página que muestra las rutas
        page.navigate(baseUrl + "/rutas");

        // Validar que solo la ruta "Ruta 1" se muestre correctamente
        page.locator(".ruta-name:has-text('Ruta 1')").waitFor();

        // Verificar que el contenido es el esperado
        String rutaNombre = page.locator(".ruta-name:has-text('Ruta 1')").textContent();

        // Asegúrate de que se muestra correctamente
        assertEquals("Ruta 1", rutaNombre);
    }

    @Test
    void editarRutaCorrecto() {
        // Navegar a la página de edición de la ruta
        page.navigate(baseUrl + "/rutas/editar/1");

        // Esperar a que el campo de entrada de nombre sea visible
        page.locator("#nombre").waitFor();

        // Editar el nombre de la ruta
        page.locator("#nombre").fill("T40 Modificada");

        // Hacer clic en el botón de actualizar
        page.locator(".btn-update").click();

        // Esperar a que aparezca un elemento específico de la nueva página
        page.waitForSelector("#ruta1 .nombre");

        String nombreRutaActualizado = page.locator("#ruta1 .nombre").textContent();
        assertEquals("T40 Modificada", nombreRutaActualizado);
    }

}
