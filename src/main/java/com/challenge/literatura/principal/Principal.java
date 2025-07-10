package com.challenge.literatura.principal;

import com.challenge.literatura.dto.JsonDTO;
import com.challenge.literatura.model.Autor;
import com.challenge.literatura.model.DatosAutor;
import com.challenge.literatura.model.DatosLibros;
import com.challenge.literatura.model.Libros;
import com.challenge.literatura.repository.AutorRepository;
import com.challenge.literatura.repository.LibrosRepository;
import com.challenge.literatura.service.ConsumoAPI;
import com.challenge.literatura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private JsonDTO resultado;
    private LibrosRepository librosRepository;
    private AutorRepository autorRepository;
    private final String URL_BASE = "https://gutendex.com/books/";


    public Principal(LibrosRepository librosRepository, AutorRepository autorRepository) {
        this.librosRepository = librosRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Agregar Libro 
                    2 - Mostrar Todos los Libros
                    3 - Listar Autores
                    4 - Listar Libros por idioma
                    5 - Listar Autor(es) vivo en X año
                    6 - Top 10 Libros mas descargados
                    7 - Buscar Autor por nombre                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Elige una opción válida");
                teclado.nextLine();
            }

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarLibrosPorIdioma();
                    break;
                case 5:
                    listarAutoresVivos();
                    break;
                case 6:
                    top10Libros();
                    break;
                case 7:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    private void buscarLibroWeb() {
        System.out.print("Escriba el nombre del libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        resultado = conversor.obtenerDatos(json, JsonDTO.class);

        Optional<Libros> resultadoLibro = resultado.resultadoLibros()
                .stream()
                .findFirst()
                .map(l -> new Libros(l));

        if (resultadoLibro.isPresent()) {
            Libros libro = resultadoLibro.get();
            System.out.println("Libro obtenido: " + libro);

            if (libro.getAutor() != null) {
                // Buscar autor por nombre
                Autor autorExistente = autorRepository.findAutorsByNombre(libro.getAutor().getNombre());

                if (autorExistente == null) {
                    autorExistente = autorRepository.save(libro.getAutor());
                }

                // Asociar el autor al libro
                libro.setAutor(autorExistente);

                // Verificar si el libro ya está en la base de datos (por ejemplo, por título y autor)
                Libros libroExistente = librosRepository.findByTitulo(libro.getTitulo());
                if (libroExistente == null) {
                    librosRepository.save(libro);
                    System.out.println("Libro guardado: " + libro);
                } else {
                    System.out.println("El libro ya se encuentra registrado en la base de datos.");
                }
            } else {
                System.out.println("No se encontró autor para este libro.");
            }
        } else {
            System.out.println("No se encontró ningún libro con ese nombre.");
        }

    }

    private void mostrarLibros() {
        List<Libros> libros = librosRepository.findAll();
        System.out.println("------------ Lista de Libros ------------");
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        System.out.println("------------ Lista de Autores ------------");
        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Seleccione el idioma de los libros que desea buscar");
        System.out.println("""
        1 -> Español
        2 -> Inglés
        3 -> Francés
        4 -> Portugués
        """);

        String idioma = "";
        int opcion;

        try {
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1 -> idioma = "es";
                case 2 -> idioma = "en";
                case 3 -> idioma = "fr";
                case 4 -> idioma = "pt";
                default -> System.out.println("Opción no válida");
            }

            List<Libros> libros = librosRepository.librosPorIdioma(idioma);
            if (libros.isEmpty()) {
                System.out.println("No existen Libros en el idioma seleccionado");
            }else {
                libros.forEach(System.out::println);
            }

        } catch (InputMismatchException e) {
            System.out.println("Elija una opción válida");
            teclado.nextLine();
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Buscar Autor(es) vivo en determinado año");
        System.out.print("Ingresa el año: ");
        try {
            int anio = teclado.nextInt();
            List<Autor> autores = autorRepository.findAutorBetweenAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No hay registros de autores de año " + anio);
            } else {
                System.out.println("Los Autores vivos en el año " + anio);
                autores.forEach(System.out::println);
            }
        } catch (InputMismatchException e){
            System.out.println("Ingresa un año válido");
            teclado.nextLine();
        }
    }

    private void top10Libros(){
        List<Libros> libros = librosRepository.top10Libros();
        if (libros.isEmpty()) {
            System.out.println("No hay un top 10 de libros");
        }else {
            libros.forEach(System.out::println);
        }
    }

    private void buscarAutorPorNombre(){
        System.out.println("Buscar Autor por nombre");
        System.out.print("Escriba el nombre: ");
        String nombreAutor = teclado.nextLine();

        List<Autor> autor = autorRepository.findAutorPorNombre(nombreAutor);
        if (autor.isEmpty()) {
            System.out.println("No hay registro de autor cono ese nombre");
        }else{
            autor.forEach(System.out::println);
        }
    }

}
