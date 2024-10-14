package com.co.init;

import com.co.model.Asignacion;
import com.co.model.Bus;
import com.co.model.Conductor;
import com.co.model.Ruta;
import com.co.repository.AsignacionRepository;
import com.co.repository.BusRepository;
import com.co.repository.ConductorRepository;
import com.co.repository.HorarioRepository;
import com.co.repository.RutaRepository;
import com.co.repository.EstacionRepository; // Importa EstacionRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private HorarioRepository horarioRepository;

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
        // Inicializar Horarios
        Horario horario1 = new Horario();
        horario1.setHoraInicio("08:00");
        horario1.setHoraFin("12:00");
        horario1.setDias("Lunes a Viernes");
        horarioRepository.save(horario1);

        Horario horario2 = new Horario();
        horario2.setHoraInicio("13:00");
        horario2.setHoraFin("17:00");
        horario2.setDias("Lunes a Sábado");
        horarioRepository.save(horario2);

        // Inicializar Estaciones
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Estación Central");
        estacionRepository.save(estacion1);

        Estacion estacion2 = new Estacion();
        estacion2.setNombre("Estación Norte");
        estacionRepository.save(estacion2);

        // Inicializar Rutas
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("T40");
        ruta1.setEstaciones(new HashSet<>(Set.of(estacion1, estacion2))); // Asignar estaciones a la ruta
        ruta1.getHorarioFuncionamiento().add(horario1.getId()); // Agregar ID del horario
        rutaRepository.save(ruta1);

        Ruta ruta2 = new Ruta();
        ruta2.setNombre("K86");
        ruta2.setEstaciones(new HashSet<>(Set.of(estacion1))); // Asignar una estación a la ruta
        ruta2.getHorarioFuncionamiento().add(horario2.getId()); // Agregar ID del horario
        rutaRepository.save(ruta2);

        // Inicializar Buses (sin asignación de rutas)
        Bus bus1 = new Bus();
        bus1.setNumeroPlaca("ABC123");
        bus1.setModelo("Modelo Bus 1");
        busRepository.save(bus1);

        Bus bus2 = new Bus();
        bus2.setNumeroPlaca("XYZ789");
        bus2.setModelo("Modelo Bus 2");
        busRepository.save(bus2);

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

        // Opcional: Crear asignaciones
        Asignacion asignacion1 = new Asignacion();
        asignacion1.setBus(bus1);
        asignacion1.setRuta(ruta1);
        asignacion1.setHorario(horario1);
        asignacion1.setConductor(conductor1); // Asignar conductor
        asignacionRepository.save(asignacion1);

        Asignacion asignacion2 = new Asignacion();
        asignacion2.setBus(bus2);
        asignacion2.setRuta(ruta2);
        asignacion2.setHorario(horario2);
        asignacion2.setConductor(conductor2); // Asignar conductor
        asignacionRepository.save(asignacion2);
    }
}
