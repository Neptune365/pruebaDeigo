package com.upao.biblioteca.domain.dto.libroDto;

import com.upao.biblioteca.domain.entity.Categoria;
import com.upao.biblioteca.domain.entity.Estado;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.upao.biblioteca.domain.entity.Libro}
 * Utilizado para encapsular los datos necesarios para el registro de un nuevo libro.
 * Incluye campos como título, estado, portada, edicion, categoria, resumen, etc y nombre del autor y del editorial.
 */
public record DatosRegistroLibro(String titulo, String portada, String edicion,
                                 Categoria categoria, String resumen, String isbn, @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate fechaPublicacion,
                                 String codigoPublico, List<String>autorNombres, String editorialNombre) implements Serializable {
}