package com.co.repository;

import com.co.model.Asignacion;
import com.co.model.BusRutaDia;
import com.co.model.Conductor;
import com.co.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    // Obtener asignaciones por el ID del conductor
    List<Asignacion> findByConductorId(Long conductorId);

    // Obtener asignaciones que contengan un bus específico en su lista de BusRutaDia
    @Query("SELECT a FROM Asignacion a JOIN a.busRutaDias brd WHERE brd.bus.id = :busId")
    List<Asignacion> findByBusId(Long busId);

    // Obtener buses de asignaciones sin conductor
    @Query("SELECT DISTINCT brd.bus FROM Asignacion a JOIN a.busRutaDias brd WHERE a.conductor IS NULL")
    List<BusRutaDia> findBusesWithoutConductor();
}
