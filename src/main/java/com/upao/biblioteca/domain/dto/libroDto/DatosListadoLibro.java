package com.upao.biblioteca.domain.dto.libroDto;

import com.upao.biblioteca.domain.entity.Categoria;
import com.upao.biblioteca.domain.entity.Estado;
import com.upao.biblioteca.domain.entity.Libro;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.upao.biblioteca.domain.entity.Libro}
 * Utilizado para representar los datos esenciales de un libro en listados o catálogos.
 * Incluye información como título, estado, portada, categoria, resumen, isbn, fecha, nombre del autor
 * y el nombre de la editorial.
 * @author Jhoel Maqui & James Huaman
 * @version 1.3
 */
public record DatosListadoLibro(String titulo, Estado estado, String portada, String edicion, Categoria categoria,
                                String resumen, String isbn, LocalDate fechaPublicacion, String autorNombre,
                                String editorialNombre) implements Serializable {
    /**
     * Construye un {@link DatosListadoLibro} basado en una entidad {@link Libro}.
     *
     * @param libro La entidad libro de la cual se extraerán los datos.
     */
    public DatosListadoLibro (Libro libro){
        this(libro.getTitulo(), libro.getEstado(), libro.getPortada(), libro.getEdicion(), libro.getCategoria(),
                libro.getResumen(), libro.getIsbn(), libro.getFechaPublicacion()
                , libro.getAutores().stream().map(autor -> autor.getNombre()).findFirst().orElse(""),
                libro.getEditorial().getNombre());
    }
}