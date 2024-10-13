package com.co.transmilenio.repository;

import com.co.transmilenio.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    boolean existsByConductorId(Long conductorId);
    boolean existsByRutaId(Long rutaId);
    List<Asignacion> findByBusesId(Long busId);

    List<Asignacion> findByConductorId(Long conductorId);
}
