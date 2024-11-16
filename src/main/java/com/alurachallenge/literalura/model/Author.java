package com.alurachallenge.literalura.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    @JsonProperty("birth_year")
    private Integer birthYear;
    @JsonProperty("death_year")// Cambiado a Integer para permitir valores null
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

    private List<Book>  books;

    public Author (){}


    public Author(DatosAutor autor) {
        this.name = autor.name();
        this.birthYear = autor.birthyear();
        this.deathYear = autor.deathyear();

    }

    public String get_formated_author_name() {
        var tmp = name.split(", ");
        if (tmp.length > 1) return tmp[1] + " " + tmp[0];
        else return tmp[0];
    }
    @Override
    public String toString() {
        StringBuilder output_string = new StringBuilder("=".repeat(120) + "\n" +
                "name      : " + this.get_formated_author_name() + "\n" +
                "birth year: " + birthYear + "\n" +
                "death year: " + deathYear);
        return output_string.toString();
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    }

