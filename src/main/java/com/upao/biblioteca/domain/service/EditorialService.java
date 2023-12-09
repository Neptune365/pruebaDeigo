package com.upao.biblioteca.domain.service;

import com.upao.biblioteca.domain.entity.Editorial;
import com.upao.biblioteca.infra.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EditorialService {

    @Autowired
    private final EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public Optional<Editorial> buscarPorNombre(String nombre) {
        return editorialRepository.findByNombre(nombre);
    }

    public Editorial guardarEditorial(Editorial editorial) {
        try {
            return editorialRepository.save(editorial);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la editorial", e);
        }
    }

}
