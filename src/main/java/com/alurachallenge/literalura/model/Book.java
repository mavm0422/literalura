package com.alurachallenge.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    @Column(unique = true, length = 500)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "autor_id")
    private Author author;


    private String languages;
    private Integer downloads;

    public Book(){};

    public Book(DatosBook datosBook) {
        this.bookId = datosBook.bookId();
        this.title = datosBook.title();
        if (datosBook.author() != null && !datosBook.author().isEmpty()) {
        this.author = datosBook.author().get(0);
        } else {
            // Si no hay autores, asignar null
            this.author = null;}

        this.languages = languageChange(datosBook.languages());
        this.downloads = datosBook.download_count();
    }


    public Book(Book book) {

    }

    private String languageChange(List<String> languages) {
        if (languages == null || languages.isEmpty()) {
            return "Desconocido";
        }
        return languages.get(0);
    }


    @Override
    public String toString() {
        return "******* LIBRO GUARDADO ***********\n" +
                "Título: " + title + "\n" +
                "Autor: " + (author != null ? author.getName() : "Desconocido") + "\n" +
                "Idioma: " + (languages != null ? languages : "Desconocido") + "\n" +
                "Número de descargas: " + (downloads != null ? downloads : 0) + "\n" +
                "**********************************";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
}

// Métodos getter y setter






