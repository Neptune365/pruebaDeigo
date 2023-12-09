package com.upao.biblioteca.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "solcitudes")
@Entity(name = "Solicitud")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "solicitudId")
public class Solicitud {
    @Id
    @Column(name = "solicitud_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solicitudId;
    @Column(name = "fecha_solicitada", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime fechaSolicitada;
    @Column(name = "fecha_recojo")
    private LocalDateTime fechaRecojo;
    @Column(name = "fecha_max_devolucion")
    private LocalDateTime fechaMaxDevolucion;
    @Column(name = "fecha_devolucion")
    private LocalDateTime fechaDevolucion;
    @ManyToOne
    @JoinColumn(name = "libro_id" )
    private Libro libro;
    @ManyToOne
    @JoinColumn(name= "usuario_id")
    private Usuario usuario;
}
