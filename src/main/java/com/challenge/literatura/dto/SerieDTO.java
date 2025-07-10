package com.challenge.literatura.dto;

import com.challenge.literatura.model.Autor;

import java.util.List;

public record SerieDTO(Long id,
        String titulo,
        List<Autor>autor,
        String resumen,
        Long numeroDescargas
) {
}
