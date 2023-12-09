package com.upao.biblioteca.infra.repository;

import com.upao.biblioteca.domain.entity.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Page<Libro> findAllByOrderByTituloAsc(Pageable pageable);
    Optional<Libro> findByCodigoPublico(String codigoPublico);
}
