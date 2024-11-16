package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibrosRespuestaApi {

    @JsonAlias("results")
    List<DatosBook> resultadoLibros;

    public List<DatosBook> getResultadoLibros() {
        return resultadoLibros;
    }

    public void setResultadoLibros(List<DatosBook> resultadoLibros) {
        this.resultadoLibros = resultadoLibros;
    }
}
