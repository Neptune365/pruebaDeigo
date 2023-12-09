package com.upao.biblioteca.infra.repository;

import com.upao.biblioteca.domain.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditorialRepository extends JpaRepository<Editorial, Long> {
    Optional<Editorial> findByNombre(String nombre);
}
