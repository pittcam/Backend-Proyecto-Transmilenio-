package com.co.repository;

import com.co.model.BusRutaDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRutaDiaRepository extends JpaRepository<BusRutaDia, Long> {
    // Puedes agregar más métodos personalizados si son necesarios en el futuro
}
