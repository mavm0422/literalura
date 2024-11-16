package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Author;
import com.alurachallenge.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitle(String title);
    List<Book> findByLanguages(String Language);





}


