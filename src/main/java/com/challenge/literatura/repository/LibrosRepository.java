package com.challenge.literatura.repository;

import com.challenge.literatura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libros,Long> {
    Libros findByTitulo(String titulo);

    @Query("SELECT l FROM Libros l WHERE l.idiomas = :idioma")
    List<Libros> librosPorIdioma(String idioma);

    @Query("SELECT l FROM Libros l ORDER By l.numeroDescargas DESC LIMIT 10")
    List<Libros> top10Libros();

}
