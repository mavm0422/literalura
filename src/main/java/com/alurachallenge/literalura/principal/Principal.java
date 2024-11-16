package com.alurachallenge.literalura.principal;

import com.alurachallenge.literalura.model.Author;
import com.alurachallenge.literalura.model.Book;
import com.alurachallenge.literalura.model.DatosBook;
import com.alurachallenge.literalura.model.LibrosRespuestaApi;
import com.alurachallenge.literalura.repository.AuthorRepository;
import com.alurachallenge.literalura.repository.BookRepository;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URLAPI = "https://gutendex.com/books/";
    private List<Book> datosBook = new ArrayList<>();
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private List<Book> search_history  = new ArrayList<>();

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void muestraElMenu() {


        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    > ***********************************<
                    > << eliga una opción a consultar >> <
                    > ***********************************<
                    1 - buscar Libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores resgistrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    
                    |***************************************************|
                    |*****            INGRESE UNA OPCIÓN          ******|
                     ***************************************************|
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarBookWeb();
                        break;
                    case 2:
                        bookRegistered();
                        break;
                    case 3:
                        authorsRegistered();
                        break;
                    case 4:
                        authorsByYear();
                        break;
                    case 5:
                        findByLanguage();
                        break;



                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        teclado.close();
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("**************************************");
                System.out.println("Por favor, introduce un número válido.");
                System.out.println("**************************************");
                teclado.nextLine();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }




    private Book getDatosBook() throws JsonProcessingException {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreBook = teclado.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(URLAPI + "?search=" + nombreBook.replace(" ", "%20"));
        System.out.println(json);
        LibrosRespuestaApi datos = conversor.obtenerDatos(json, LibrosRespuestaApi.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosBook firstBook = datos.getResultadoLibros().get(0);
            return new Book(firstBook);
        } else {
            System.out.println("No se encontraron resultados");
            return null;
        }

    }

    private void buscarBookWeb() throws JsonProcessingException {
        Book book = getDatosBook();
        if (book == null) {
            System.out.println("Libro no encontrado. El valor es null");
            return;
        }

        // Validación del título del libro
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            System.out.println("El título del libro es inválido.");
            return;
        }

        try {
            boolean libroExists = bookRepository.existsByTitle(book.getTitle());
            if (libroExists) {
                System.out.println("El libro ya existe en la base de datos!");
            } else {
                Author author = book.getAuthor();
                if (author == null || author.getName() == null || author.getName().isEmpty()) {
                    System.out.println("El libro no tiene un autor válido.");
                    return;
                }
                // Verificar si el autor ya existe en la base de datos
                Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
                if (existingAuthor.isPresent()) {
                    System.out.println("El autor ya existe en la base de datos!");
                    // Vincular el libro al autor existente
                    book.setAuthor(existingAuthor.get());
                } else {
                    // Guardar el nuevo autor
                    author = authorRepository.save(author);
                    book.setAuthor(author);
                }
                bookRepository.save(book);
                System.out.println(book.toString());
            }
        } catch (Exception e) {
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void bookRegistered() {
        System.out.println("=".repeat(120));
        System.out.println("Libros Registrados");

        List<Book> allBooks = bookRepository.findAll(); // Obtiene todos los libros de la base de datos
        if (!allBooks.isEmpty()) {
            allBooks.forEach(book -> {
                System.out.println(book.toString());
                System.out.println("=".repeat(120));
            });
        } else {
            System.out.println("No hay libros registrados en la base de datos.");
        }
        continue_msg();
    }

    public List<Book> getSearch_history() {
        return search_history;
    }

    public void setSearch_history(List<Book> search_history) {
        this.search_history = search_history;
    }

    public void setDatosBook(List<Book> datosBook) {
        this.datosBook = datosBook;
    }

    private void authorsRegistered() {
        List<Author> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores en la base de datos. \n");
        } else {
            System.out.println("Autores encontrados en la base de datos: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Author author : authors) {
                // add() retorna true si el nombre no estaba presente y se añade correctamente
                if (autoresUnicos.add(author.getName())){
                    System.out.println(author.getName()+'\n');
                }
            }
        }
        continue_msg();
    }

    private void authorsByYear() {

        System.out.println("Indica el año para consultar que autores estan vivos: \n");
        var yearSearch = teclado.nextInt();
        teclado.nextLine();

        List<Author> authorsAlive = authorRepository.findByBirthYearLessThanOrDeathYearGreaterThanEqual(yearSearch, yearSearch);

        if (authorsAlive.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + yearSearch + ".");
        } else {
            System.out.println("Los autores que estaban vivos en el año " + yearSearch + " son:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Author author : authorsAlive) {
                if (author.getBirthYear() != null && author.getDeathYear() != null) {
                    if (author.getBirthYear() <= yearSearch && author.getDeathYear() >= yearSearch) {
                        if (autoresUnicos.add(author.getName())) {
                            System.out.println("Autor: " + author.getName());
                        }
                    }
                }
            }
        }
    }

    private void findByLanguage() {

        System.out.println("Ingrese Idioma en el que quiere buscar: \n");
        System.out.println("|***********************************|");
        System.out.println("|  Opción - es : Libros en español. |");
        System.out.println("|  Opción - en : Libros en ingles.  |");
        System.out.println("|  Opción - fr : Libros en frances.  |");
        System.out.println("|***********************************|\n");

        var language = teclado.nextLine();
        List<Book> booksByLanguage = bookRepository.findByLanguages(language);

        if (booksByLanguage.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros segun idioma encontrados en la base de datos:");
            for (Book book : booksByLanguage) {
                System.out.println(book.toString());
            }
        }

    }

    private void continue_msg(){
        System.out.print("Presiona Enter para continuar.");
        teclado.nextLine();
    }
}









