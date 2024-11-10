package com.co.init;

import com.co.model.*;
import com.co.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Profile("integration-testing")
public class DBInitializerTest implements CommandLineRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private BusRutaDiaRepository busRutaDiaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Crear algunas estaciones de ejemplo
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Terminal Norte");
        estacion1 = estacionRepository.save(estacion1);

        Estacion estacion2 = new Estacion();
        estacion2.setNombre("Calle 34");
        estacion2 = estacionRepository.save(estacion2);

        // Crear rutas de ejemplo con días específicos y estaciones
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("T40");
        ruta1.setHoraInicio("5:00");
        ruta1.setHoraFin("22:00");
        ruta1.setDias(new ArrayList<>(Set.of("Lunes", "Martes", "Miércoles")));
        ruta1.setEstaciones(new ArrayList<>(Set.of(estacion1, estacion2)));
        ruta1 = rutaRepository.save(ruta1);

        // Crear buses de ejemplo
        Bus bus1 = new Bus();
        bus1.setPlaca("TEST123");
        bus1.setModelo("Modelo Prueba 1");
        bus1.setRutas(new ArrayList<>(Set.of(ruta1)));
        bus1 = busRepository.save(bus1);

        // Crear BusRutaDia
        BusRutaDia busRutaDia1 = new BusRutaDia();
        busRutaDia1.setRuta(ruta1);
        busRutaDia1.setDias(new ArrayList<>(Set.of("Lunes", "Martes")));
        busRutaDia1.setBus(bus1);
        busRutaDia1 = busRutaDiaRepository.saveAndFlush(busRutaDia1);

        // Crear conductores de prueba
        Conductor conductor1 = new Conductor();
        conductor1.setNombre("Carlos Test");
        conductor1.setCedula("111222333");
        conductor1.setTelefono("3123456789");
        conductor1.setDireccion("Test Street 123");
        conductor1 = conductorRepository.save(conductor1);

        // Crear asignaciones de prueba
        Asignacion asignacion1 = new Asignacion();
        asignacion1.setConductor(conductor1);
        asignacion1.setBusRutaDias(new ArrayList<>(List.of(busRutaDia1)));
        asignacionRepository.save(asignacion1);
    }
}