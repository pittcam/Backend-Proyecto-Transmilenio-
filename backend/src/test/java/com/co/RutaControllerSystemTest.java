package com.co;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void eliminarRutaCorrecto() {
        // Navegar a la página que muestra las rutas
        page.navigate(baseUrl + "/rutas");

        // Esperar que la ruta "Ruta 1" esté visible antes de eliminar
        page.locator("text=Ruta 1").waitFor();

        // Localizar el botón "Eliminar" dentro del contenedor de "Ruta 1" y hacer clic en él
        page.locator("text=Ruta 1").locator("..").locator(".btn-delete").click();

        // Esperar que la interfaz de usuario se actualice (esperamos que la ruta sea eliminada)
        page.waitForTimeout(1000); // Espera de 1 segundo para que se actualice la UI

        // Verificar que "Ruta 1" ya no esté en la lista
        boolean isRuta1Visible = page.locator("text=Ruta 1").isVisible();
        assertEquals(false, isRuta1Visible, "La ruta 'Ruta 1' debería haber sido eliminada.");
    }

    @Test
    void crearRutaCorrecto() {
        page.navigate(baseUrl + "/rutas/crear");

        page.fill("input[name='nombre']", "Ruta Test");

        page.click(".btn-create");

        page.waitForTimeout(1000);

        // Verificar que la nueva ruta se haya agregado a la lista
        boolean isRutaTestVisible = page.locator("text=Ruta Test").isVisible();
        assertTrue(isRutaTestVisible, "La ruta 'Ruta Test' debería haberse creado y mostrado en la pantalla.");
    }


    @Test
    void editarRutaCorrecto() {
        // Crear una ruta "Ruta 1" como en el test anterior

        // Navegar a la página que lista las rutas
        page.navigate(baseUrl + "/rutas");

        // Esperar que la ruta "Ruta 1" esté visible
        page.locator("text=Ruta 1").waitFor();

        // Localizar el botón "Editar" dentro del contenedor de "Ruta 1" y hacer clic en él
        page.locator("text=Ruta 1").locator("..").locator(".btn-edit").click();

        // Esperar que el formulario de edición de la ruta esté visible
        page.locator("input[name='nombre']").waitFor();

        // Modificar el nombre de la ruta en el campo de texto
        page.fill("input[name='nombre']", "Ruta Editada");

        // Hacer clic en el botón "Actualizar Ruta"
        page.click(".btn-update");

        // Esperar que la interfaz de usuario se actualice
        page.waitForTimeout(1000);

        // Verificar que la nueva ruta se haya actualizado en la lista
        boolean isRutaEditadaVisible = page.locator("text=Ruta Editada").isVisible();
        assertTrue(isRutaEditadaVisible, "La ruta debería haberse actualizado a 'Ruta Editada' en la pantalla.");
    }


}
