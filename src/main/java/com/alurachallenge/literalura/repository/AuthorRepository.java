package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByBirthYearLessThanOrDeathYearGreaterThanEqual(int birthYear, int deathYear);

    boolean existsByName(String name);

    @Query("select a from Author a where a.name=:name")
    Author findbyname(@Param("name") Integer name);
    @Query("select a from Author a where  a.birthYear<=:year and a.deathYear>=:year")
    List<Author> findbydate(@Param("year") Integer year);
    @Query("select a from Author a where a.name ilike %:name%")
    List<Author> findauthorsbyname(@Param("name") Integer name);
    @Query("select a from Author a where a.birthYear=:year")
    List<Author> findauthorsbybirthyear(@Param("year") Integer year);
    @Query("select a from Author a where a.deathYear=:year")
    List<Author> findauthorsbydeathyear(@Param("year") Integer year);

    Optional<Author> findByName(String name);
}
