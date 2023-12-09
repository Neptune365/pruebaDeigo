package com.upao.biblioteca.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "editoriales")
@Entity(name = "Editorial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "editorialId")
public class Editorial {
    /**
     * Identificador único de la editorial.
     */
    @Id
    @Column(name = "editorial_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long editorialId;
    /**
     * Nombre de la editorial.
     */
    @Column(nullable = false, length = 155)
    private String nombre;

    @OneToMany(mappedBy = "editorial")
    private List<Libro> libro;
}
