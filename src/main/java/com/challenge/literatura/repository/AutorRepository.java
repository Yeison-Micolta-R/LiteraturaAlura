package com.challenge.literatura.repository;

import com.challenge.literatura.model.Autor;
import com.challenge.literatura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {
    Autor findAutorsByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE :anio >= a.annioNacimiento AND :anio <= a.annioMuerte")
    List<Autor> findAutorBetweenAnio(int anio);

    @Query("SELECT a FROM Libros l JOIN l.autor a WHERE a.nombre ILIKE %:nombreAutor%")
    List<Autor>findAutorPorNombre(String nombreAutor);
}
