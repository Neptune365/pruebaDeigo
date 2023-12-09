package com.upao.biblioteca.infra.repository;

import com.upao.biblioteca.domain.entity.Libro;
import com.upao.biblioteca.domain.entity.Solicitud;
import com.upao.biblioteca.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    boolean existsByUsuarioAndLibro(Usuario usuario, Libro libro);
    long countByUsuario(Usuario usuario);
}
