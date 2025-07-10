package com.challenge.literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer annioNacimiento;
    private Integer annioMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.annioNacimiento = datosAutor.annioNacimiento();
        this.annioMuerte = datosAutor.annioMuerte();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        String [] n = nombre.split(",");
        if (n.length == 2) {
            nombre = n[1].trim() +" "+ n[0];
            return nombre;
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnnioNacimiento() {
        return annioNacimiento;
    }

    public void setAnnioNacimiento(Integer annioNacimiento) {
        this.annioNacimiento = annioNacimiento;
    }

    public Integer getAnnioMuerte() {
        return annioMuerte;
    }

    public void setAnnioMuerte(Integer annioMuerte) {
        this.annioMuerte = annioMuerte;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "Nombre: " + nombre + "\n"+
                "Año de Nacimiento: " + annioNacimiento + "\n"+
                "Año de Muerte: " + annioMuerte + "\n";
    }
}
