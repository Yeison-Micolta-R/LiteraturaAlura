package com.challenge.literatura.dto;

import com.challenge.literatura.model.DatosLibros;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JsonDTO(
        @JsonAlias("results") List<DatosLibros> resultadoLibros
) {
}
