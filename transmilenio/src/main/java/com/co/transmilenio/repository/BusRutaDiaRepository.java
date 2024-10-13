package com.co.transmilenio.repository;

import com.co.transmilenio.model.BusRutaDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRutaDiaRepository extends JpaRepository<BusRutaDia, Long> {
}
