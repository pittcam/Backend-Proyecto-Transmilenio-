package com.co.transmilenio.repository;

import com.co.transmilenio.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // No es necesario agregar métodos adicionales aquí, JPA proporcionará los métodos CRUD automáticamente
}