package com.co.init;


import com.co.model.*;

import com.co.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private EstacionRepository estacionRepository; // Si es necesario

    @Override
    public void run(String... args) throws Exception {

        // Inicializar Estaciones
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Terminal Norte");
        estacionRepository.save(estacion1);

        Estacion estacion2 = new Estacion();
        estacion2.setNombre("Calle 34");
        estacionRepository.save(estacion2);


        // Inicializar Rutas
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("T40");
        ruta1.setHoraInicio("5:00");
        ruta1.setHoraFin("22:30");
        ruta1.setEstaciones(new HashSet<>(Set.of(estacion1, estacion2))); // Asignar estaciones a la ruta
        rutaRepository.save(ruta1);

        Ruta ruta2 = new Ruta();
        ruta2.setNombre("B18");
        ruta2.setHoraInicio("5:00");
        ruta2.setHoraFin("22:30");
        ruta2.setEstaciones(new HashSet<>(Set.of(estacion1, estacion2))); // Asignar estaciones a la ruta
        rutaRepository.save(ruta1);


        // Inicializar Buses (sin asignación de rutas)
        Bus bus1 = new Bus();
        bus1.setPlaca("JDK345");
        bus1.setModelo("Modelo Bus 1");
        busRepository.save(bus1);

        BusRutaDia rutaBus1 = new BusRutaDia();
        rutaBus1.setRuta(ruta1);
        rutaBus1.setDias(new ArrayList<>(Set.of('L', 'M')));
        rutaBus1.setBus(bus1);

        Bus bus2 = new Bus();
        bus2.setPlaca("XYZ789");
        bus2.setModelo("Modelo Bus 2");
        busRepository.save(bus2);

        BusRutaDia rutaBus2 = new BusRutaDia();
        rutaBus2.setRuta(ruta1);
        rutaBus2.setDias(new ArrayList<>(Set.of('M', 'J','S')));
        rutaBus2.setBus(bus2);

        Bus bus3 = new Bus();
        bus3.setPlaca("ABC123");
        bus3.setModelo("Modelo Bus 3");
        busRepository.save(bus3);

        Bus bus4 = new Bus();
        bus4.setPlaca("AKR552");
        bus4.setModelo("Modelo Bus 4");
        busRepository.save(bus4);

        // Inicializar Conductores
        Conductor conductor1 = new Conductor();
        conductor1.setNombre("Juan Pérez");
        conductor1.setCedula("12345678");
        conductor1.setTelefono("3001234567");
        conductor1.setDireccion("Calle 1 # 2-3");
        conductorRepository.save(conductor1);

        Conductor conductor2 = new Conductor();
        conductor2.setNombre("María Rodríguez");
        conductor2.setCedula("87654321");
        conductor2.setTelefono("3017654321");
        conductor2.setDireccion("Carrera 4 # 5-6");
        conductorRepository.save(conductor2);

        Conductor conductor3 = new Conductor();
        conductor2.setNombre("Camila Neiza");
        conductor2.setCedula("1019982134");
        conductor2.setTelefono("3133992341");
        conductor2.setDireccion("Calle 7 # 24-32");
        conductorRepository.save(conductor2);

        // Opcional: Crear asignaciones
        Asignacion asignacion1 = new Asignacion();
        asignacion1.setConductor(conductor1);
        asignacion1.setBusRutaDias(new ArrayList<>(List.of(rutaBus1)));
        asignacionRepository.save(asignacion1);

        Asignacion asignacion2 = new Asignacion();
        asignacion1.setConductor(conductor1);
        asignacion2.setBusRutaDias(new ArrayList<>(List.of(rutaBus2)));
        asignacionRepository.save(asignacion2);
    }
}