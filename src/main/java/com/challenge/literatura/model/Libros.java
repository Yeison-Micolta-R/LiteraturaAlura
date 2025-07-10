package com.challenge.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idiomas;
    private Long numeroDescargas;

    public Libros() {
    }

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.autor = new Autor(datosLibros.autor().get(0));
        this.idiomas = datosLibros.idiomas().get(0);
        this.numeroDescargas = datosLibros.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return
                        "Id libro: " + id + "\n" +
                        "Titulo: " + titulo + "\n" +
                        "Autor: " + autor.getNombre() + "\n" +
                        "Año de nacimiento: " + autor.getAnnioNacimiento() + "\n" +
                        "Año de fallecimiento: " + autor.getAnnioMuerte() + "\n" +
                        "Idioma: " + idiomas + "\n" +
                        "Número de descargas: " + numeroDescargas + "\n";
    }
}
