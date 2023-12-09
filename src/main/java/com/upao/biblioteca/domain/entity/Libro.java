package com.upao.biblioteca.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representa un libro en el contexto de una biblioteca.
 * Cada libro tiene un identificador único, título, estado, portada y está asociado a un autor.
 * Esta entidad es parte integral del modelo de datos de la biblioteca, vinculándose con la entidad Autor.
 */
@Table(name = "libros")
@Entity(name = "Libro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "libroId")
public class Libro {
    /**
     * Identificador único del libro.
     */
    @Id
    @Column(name = "libro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libroId;
    /**
     * Título del libro.
     */
    private String titulo;
    /**
     * Estado actual del libro (por ejemplo: disponible, prestado).
     * Se utiliza un tipo enumerado para representar los diferentes estados.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;
    /**
     * Imagen de la portada del libro.
     * Se almacena como un objeto Lob (Large Object) debido a su potencial tamaño grande.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String portada;
    private String edicion;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String resumen;
    private String isbn;
    @Column(name = "fecha_publicacion")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate fechaPublicacion;
    @Column(unique = true)
    private String codigoPublico;
    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id_fk")
    private Editorial editorial;

    public Set<Autor> getAutores() {
        return autores;
    }
    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Set<String> getNombresAutores() {
        return autores.stream()
                .map(autor -> autor.getNombre())
                .collect(Collectors.toSet());
    }

    public void reservarLibro() {
        this.estado = Estado.RESERVADO;
    }

    public void marcarNoDisponible() {
        this.estado = Estado.NODISPONIBLE;
    }

    public void marcarDisponible(){
        this.estado = Estado.DISPONIBLE;
    }

}
