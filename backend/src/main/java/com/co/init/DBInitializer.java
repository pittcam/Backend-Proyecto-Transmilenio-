package com.co.init;


import com.co.model.*;

import com.co.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private BusRutaDiaRepository busRutaDiaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EstacionRepository estacionRepository;

    @Override
    public void run(String... args) throws Exception {

        Rol rol1 = new Rol();
        rol1.setTipoRol("ADMIN");
        Rol rol2 = new Rol();
        rol2.setTipoRol("USER");
        Rol rol3 = new Rol();
        rol3.setTipoRol("COORDINADOR");
        roleRepository.save(rol1);
        roleRepository.save(rol2);
        roleRepository.save(rol3);


        User user1 = new User();
        user1.setNombre("camila");
        user1.setCedula("12345678");
        user1.setCorreo("cam@exam.co");
        user1.setUsername("user");
        user1.setContrasena(passwordEncoder.encode("userpass"));
        user1.setRol(rol2);
        userRepository.save(user1);

        User user2 = new User();
        user2.setNombre("Juan");
        user2.setCedula("123455");
        user2.setCorreo("juan@exam.co");
        user2.setUsername("admin");
        user2.setContrasena(passwordEncoder.encode("adminpass"));
        user2.setRol(rol1);
        userRepository.save(user2);

        User user3 = new User();
        user3.setNombre("Jerry");
        user3.setCedula("123457");
        user3.setCorreo("Jerry@gmail.com");
        user3.setUsername("coordinador");
        user3.setContrasena(passwordEncoder.encode("coordinadorpass"));
        user3.setRol(rol3);
        userRepository.save(user3);

        // Inicializar Estaciones
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Terminal Norte");
        estacionRepository.save(estacion1);

        Estacion estacion2 = new Estacion();
        estacion2.setNombre("Calle 34");
        estacionRepository.save(estacion2);

        Estacion estacion3 = new Estacion();
        estacion3.setNombre("Portal Usme");
        estacionRepository.save(estacion3);


        // Inicializar Rutas
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("T40");
        ruta1.setHoraInicio("5:00");
        ruta1.setHoraFin("22:00");
        ruta1.setDias(new ArrayList<>(Set.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")));
        ruta1.setEstaciones(new ArrayList<>(Set.of(estacion1, estacion2))); // Asignar estaciones a la ruta
        rutaRepository.save(ruta1);

        Ruta ruta2 = new Ruta();
        ruta2.setNombre("B18");
        ruta2.setHoraInicio("5:00");
        ruta2.setHoraFin("22:30");
        ruta2.setDias(new ArrayList<>(Set.of("Lunes", "Jueves", "Sábado")));
        ruta2.setEstaciones(new ArrayList<>(Set.of(estacion1, estacion2))); // Asignar estaciones a la ruta
        rutaRepository.save(ruta2);

        Ruta ruta3 = new Ruta();
        ruta3.setNombre("K86");
        ruta3.setHoraInicio("4:00");
        ruta3.setHoraFin("19:30");
        ruta3.setDias(new ArrayList<>(Set.of("Martes", "Miércoles", "Sábado")));
        ruta3.setEstaciones(new ArrayList<>(Set.of(estacion1, estacion3))); // Asignar estaciones a la ruta
        rutaRepository.save(ruta3);


        // Inicializar Buses (sin asignación de rutas)
        Bus bus1 = new Bus();
        bus1.setPlaca("JDK345");
        bus1.setModelo("Modelo Bus 1");
        bus1.setRutas(new ArrayList<>(Set.of(ruta1, ruta2)));
        busRepository.save(bus1);

        BusRutaDia rutaBus1 = new BusRutaDia();
        rutaBus1.setRuta(ruta1);
        rutaBus1.setDias(new ArrayList<>(Set.of("Lunes", "Martes")));
        rutaBus1.setBus(bus1);

        Bus bus2 = new Bus();
        bus2.setPlaca("XYZ789");
        bus2.setModelo("Modelo Bus 2");
        bus2.setRutas(new ArrayList<>(Set.of(ruta3)));
        busRepository.save(bus2);


        BusRutaDia rutaBus2 = new BusRutaDia();
        rutaBus2.setRuta(ruta1);
        rutaBus2.setDias(new ArrayList<>(Set.of("Miércoles", "Jueves","Sábado")));
        rutaBus2.setBus(bus2);

        Bus bus3 = new Bus();
        bus3.setPlaca("ABC123");
        bus3.setModelo("Modelo Bus 3");
        bus3.setRutas(new ArrayList<>(Set.of(ruta1, ruta3)));
        busRepository.save(bus3);

        BusRutaDia rutaBus3 = new BusRutaDia();
        rutaBus3.setRuta(ruta3);
        rutaBus3.setDias(new ArrayList<>(Set.of("Miércoles", "Jueves","Sábado")));
        rutaBus3.setBus(bus3);
        busRutaDiaRepository.save(rutaBus3);

        Bus bus4 = new Bus();
        bus4.setPlaca("AKR552");
        bus4.setModelo("Modelo Bus 4");
        bus4.setRutas(new ArrayList<>(Set.of(ruta1, ruta2, ruta3)));
        busRepository.save(bus4);

        BusRutaDia rutaBus4 = new BusRutaDia();
        rutaBus4.setRuta(ruta2);
        rutaBus4.setDias(new ArrayList<>(Set.of("Lunes", "Martes","Viernes")));
        rutaBus4.setBus(bus4);
        busRutaDiaRepository.save(rutaBus4);

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
        conductor3.setNombre("Camila Neiza");
        conductor3.setCedula("1019982134");
        conductor3.setTelefono("3133992341");
        conductor3.setDireccion("Calle 7 # 24-32");
        conductorRepository.save(conductor3);

        // Opcional: Crear asignaciones
        Asignacion asignacion1 = new Asignacion();
        asignacion1.setConductor(conductor1);
        asignacion1.setBusRutaDias(new ArrayList<>(List.of(rutaBus1)));
        asignacionRepository.save(asignacion1);

        Asignacion asignacion2 = new Asignacion();
        asignacion2.setConductor(conductor2);
        asignacion2.setBusRutaDias(new ArrayList<>(List.of(rutaBus2)));
        asignacionRepository.save(asignacion2);
    }
}