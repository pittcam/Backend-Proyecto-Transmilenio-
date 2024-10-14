package com.co.repository;

import com.co.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    // Método para buscar conductores por nombre (contiene texto y es case insensitive)
    List<Conductor> findAllByNombreContainingIgnoreCase(String textoBusqueda);

    // Método para obtener conductores por lista de IDs
    List<Conductor> findAllById(Iterable<Long> ids);

    // Método para buscar conductores por nombre que empieza con un texto específico
    List<Conductor> findAllByNombreStartingWith(String texto);

    // Método para buscar conductores por nombre que empieza con un texto específico, ignorando mayúsculas/minúsculas
    List<Conductor> findAllByNombreStartingWithIgnoreCase(String texto);

    // Consulta personalizada utilizando JPQL para buscar conductores por nombre
    @Query("SELECT c FROM Conductor c WHERE c.nombre LIKE concat(:texto, '%')")
    List<Conductor> findAllByNombreStartingWith2(String texto);

    // Consulta personalizada utilizando JPQL para buscar conductores por nombre, ignorando mayúsculas/minúsculas
    @Query("SELECT c FROM Conductor c WHERE LOWER(c.nombre) LIKE LOWER(concat(:texto, '%'))")
    List<Conductor> findAllByNombreStartingWithIgnoreCase2(String texto);
}
