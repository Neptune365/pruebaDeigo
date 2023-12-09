package com.upao.biblioteca.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "sanciones")
@Entity(name = "Sancion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "sancionId")
public class Sancion {
    @Id
    @Column(name = "sancion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sancionId;
    @Column(name = "fecha_inicio", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
}
