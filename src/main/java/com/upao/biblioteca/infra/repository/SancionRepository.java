package com.upao.biblioteca.infra.repository;

import com.upao.biblioteca.domain.entity.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SancionRepository extends JpaRepository<Sancion, Long> {
}
