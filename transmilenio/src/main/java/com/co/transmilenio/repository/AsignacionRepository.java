package com.co.transmilenio.repository;

import com.co.transmilenio.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    boolean existsByRutaId(Long rutaId);
}
