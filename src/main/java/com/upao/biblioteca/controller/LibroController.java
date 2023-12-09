package com.upao.biblioteca.controller;

import com.upao.biblioteca.domain.dto.libroDto.DatosExistentesLibro;
import com.upao.biblioteca.domain.dto.libroDto.DatosListadoLibro;
import com.upao.biblioteca.domain.dto.libroDto.DatosRegistroLibro;
import com.upao.biblioteca.domain.entity.Autor;
import com.upao.biblioteca.domain.entity.Editorial;
import com.upao.biblioteca.domain.entity.Libro;
import com.upao.biblioteca.domain.service.AutorService;
import com.upao.biblioteca.domain.service.EditorialService;
import com.upao.biblioteca.domain.service.LibroService;
import com.upao.biblioteca.infra.repository.AutorRepository;
import com.upao.biblioteca.infra.repository.EditorialRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libro")
@CrossOrigin("https://p2biblio.netlify.app")
@RequiredArgsConstructor
public class LibroController {
    @Autowired
    private final LibroService libroService;

    @Autowired
    private final AutorService autorService;

    @Autowired
    private final EditorialService editorialService;

    public static String uploadDirectory =
            System.getProperty("user.dir") + "/src/main/resources/static/images";
    private final AutorRepository autorRepository;
    private final EditorialRepository editorialRepository;


    @GetMapping("/catalogo-general")
    public ResponseEntity<Page<DatosListadoLibro>> listarLibros(@PageableDefault(size = 8) Pageable pageable) {
        Page<DatosListadoLibro> page = libroService.obtenerLibros(pageable)
                .map(libro -> new DatosListadoLibro(
                        libro.getTitulo(),
                        libro.getEstado(),
                        libro.getPortada(),
                        libro.getEdicion(),
                        libro.getCategoria(),
                        libro.getResumen(),
                        libro.getIsbn(),
                        libro.getFechaPublicacion(),
                        libro.getCodigoPublico(),
                        libro.getEditorial().getNombre()
                ));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/catalogo-existente")
    public ResponseEntity<List<DatosExistentesLibro>> getRecompensas() {
        List<Libro> libros = libroService.obtenerLibrosExistentes();
        return ResponseEntity.ok(
                libros.stream()
                        .map(recompensa -> new DatosExistentesLibro(
                                recompensa.getTitulo(),
                                recompensa.getEstado(),
                                recompensa.getPortada(),
                                recompensa.getCodigoPublico(),
                                recompensa.getAutores().stream().map(Autor::getNombre).collect(Collectors.toSet())
                        ))
                        .collect(Collectors.toList())
        );
    }



    @PostMapping("/crear-libro")
    @Transactional
    public ResponseEntity<DatosRegistroLibro> agregarLibro(@RequestBody @Valid DatosRegistroLibro datosRegistroLibro,
                                                             UriComponentsBuilder uriComponentsBuilder) throws IOException {

        String base64Image = datosRegistroLibro.portada();
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        String originalFilename = UUID.randomUUID().toString();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
        Files.write(fileNameAndPath, imageBytes);

        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo(datosRegistroLibro.titulo());
        nuevoLibro.setPortada(base64Image);
        nuevoLibro.setEdicion(datosRegistroLibro.edicion());
        nuevoLibro.setCategoria(datosRegistroLibro.categoria());
        nuevoLibro.setResumen(datosRegistroLibro.resumen());
        nuevoLibro.setIsbn(datosRegistroLibro.isbn());
        nuevoLibro.setFechaPublicacion(datosRegistroLibro.fechaPublicacion());
        nuevoLibro.setCodigoPublico(datosRegistroLibro.codigoPublico());

        List<String> nombresAutores = datosRegistroLibro.autorNombres();
        if (nombresAutores != null) {
            Set<Autor> autores = nombresAutores.stream()
                    .map(nombre -> autorRepository.findByNombre(nombre)
                            .orElseGet(() -> autorRepository.save(new Autor(nombre))))
                    .collect(Collectors.toSet());
            nuevoLibro.setAutores(autores);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Autor es obligatorio");
        }

        String nombreEditorial = datosRegistroLibro.editorialNombre();
        if (nombreEditorial != null && !nombreEditorial.isEmpty()) {
            Editorial editorial = editorialRepository.findByNombre(nombreEditorial)
                    .orElseGet(() -> {
                        Editorial nuevaEditorial = new Editorial();
                        nuevaEditorial.setNombre(nombreEditorial);
                        return editorialRepository.save(nuevaEditorial);
                    });
            nuevoLibro.setEditorial(editorial);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de editorial es obligatorio");
        }

        Libro libroGuardado = libroService.agregarLibro(nuevoLibro);

        URI location = uriComponentsBuilder.path("/libro/{id}").buildAndExpand(libroGuardado.getLibroId()).toUri();
        return ResponseEntity.created(location).body(new DatosRegistroLibro(
                libroGuardado.getTitulo(),
                datosRegistroLibro.portada(),
                libroGuardado.getEdicion(),
                libroGuardado.getCategoria(),
                libroGuardado.getResumen(),
                libroGuardado.getIsbn(),
                libroGuardado.getFechaPublicacion(),
                libroGuardado.getCodigoPublico(),
                libroGuardado.getAutores().stream().map(Autor::getNombre).collect(Collectors.toList()),
                libroGuardado.getEditorial().getNombre()
        ));
    }
}
