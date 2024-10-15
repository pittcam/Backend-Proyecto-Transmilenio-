package com.co.repository;

import com.co.dto.BusRutaDiaDTO;
import com.co.model.BusRutaDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRutaDiaRepository extends JpaRepository<BusRutaDia, Long> {

    // Método estándar para traer todas las entidades BusRutaDia
    List<BusRutaDia> findAll();

    Optional<BusRutaDia> findByBusIdAndRutaId(Long busId, Long rutaId);
}
