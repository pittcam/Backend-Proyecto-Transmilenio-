package com.co.repository;

import com.co.model.Conductor;
import com.co.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // Metodo para buscar rutas por nombre (contiene texto y es case insensitive)
    List<Ruta> findAllByNombreContainingIgnoreCase(String textoBusqueda);

    // Metodo para obtener rutas por lista de IDs
    List<Ruta> findAllById(Iterable<Long> ids);

    // Metodo para buscar conductores por nombre que empieza con un texto específico
    List<Ruta> findAllByNombreStartingWith(String texto);

    // Metodo para buscar conductores por nombre que empieza con un texto específico, ignorando mayúsculas/minúsculas
    List<Ruta> findAllByNombreStartingWithIgnoreCase(String texto);

    // Consulta personalizada utilizando JPQL para buscar conductores por nombre
    @Query("SELECT r FROM Ruta r WHERE r.nombre LIKE concat(:texto, '%')")
    List<Ruta> findAllByNombreStartingWith2(String texto);

    /*
    // Consulta personalizada utilizando JPQL para buscar conductores por nombre, ignorando mayúsculas/minúsculas
    @Query("SELECT c FROM Conductor c WHERE LOWER(c.nombre) LIKE LOWER(concat(:texto, '%'))")
    List<Conductor> findAllByNombreStartingWithIgnoreCase2(String texto);
    */
}
