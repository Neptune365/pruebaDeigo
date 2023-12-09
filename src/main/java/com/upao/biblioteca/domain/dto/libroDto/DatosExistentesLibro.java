package com.upao.biblioteca.domain.dto.libroDto;

import com.upao.biblioteca.domain.entity.Estado;
import com.upao.biblioteca.domain.entity.Libro;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.upao.biblioteca.domain.entity.Libro}
 */
public record DatosExistentesLibro(String titulo, Estado estado, String portada, String codigoPublico,
                                   Set<String> autoreNombres) implements Serializable {
        public DatosExistentesLibro (Libro libro){
            this(libro.getTitulo(), libro.getEstado(), libro.getPortada(), libro.getCodigoPublico(),
                    libro.getAutores().stream().map(autor -> autor.getNombre()).collect(java.util.stream.Collectors.toSet())); {
        }
    }
}