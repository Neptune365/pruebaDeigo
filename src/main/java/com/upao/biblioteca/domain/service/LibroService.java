package com.upao.biblioteca.domain.service;

import com.upao.biblioteca.domain.entity.Autor;
import com.upao.biblioteca.domain.entity.Editorial;
import com.upao.biblioteca.domain.entity.Estado;
import com.upao.biblioteca.domain.entity.Libro;
import com.upao.biblioteca.infra.repository.AutorRepository;
import com.upao.biblioteca.infra.repository.EditorialRepository;
import com.upao.biblioteca.infra.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private final LibroRepository libroRepository;

    @Autowired
    private final AutorRepository autorRepository;

    @Autowired
    private final EditorialRepository editorialRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository, EditorialRepository editorialRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.editorialRepository = editorialRepository;
    }

    public Page<Libro> obtenerLibros(Pageable pageable) {
        Page<Libro> libros = libroRepository.findAllByOrderByTituloAsc(pageable);

        if (libros.isEmpty() && libros.isFirst()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Libros no encontrados");
        }

        return libros;
    }

    public List<Libro> obtenerLibrosExistentes() {
        return libroRepository.findAll();
    }


    public Libro agregarLibro(Libro libro) {
        if (libro.getCodigoPublico() != null && libroRepository.findByCodigoPublico(libro.getCodigoPublico()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este código ya existe");
        }

        Set<Autor> autores = new HashSet<>();
        for (String nombreAutor : libro.getNombresAutores()) {
            Autor autor = autorRepository.findByNombre(nombreAutor)
                    .orElseGet(() -> new Autor(nombreAutor));
            autores.add(autor);
        }
        autores = autores.stream()
                .map(autor -> autorRepository.save(autor))
                .collect(Collectors.toSet());
        libro.setAutores(autores);

        String nombreEditorial = libro.getEditorial() != null ? libro.getEditorial().getNombre() : null;
        Editorial editorial;
        if (nombreEditorial != null && !nombreEditorial.isEmpty()) {
            editorial = editorialRepository.findByNombre(nombreEditorial)
                    .orElseGet(() -> {
                        Editorial nuevaEditorial = new Editorial();
                        nuevaEditorial.setNombre(nombreEditorial);
                        return editorialRepository.save(nuevaEditorial);
                    });
            libro.setEditorial(editorial);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de editorial es obligatorio");
        }
        if (libro.getTitulo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Título es obligatorio");
        }
        if (libro.getPortada().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La Portada no puede estar vacío");
        }

        libro.setEstado(Estado.DISPONIBLE);

        return libroRepository.save(libro);
    }
}
