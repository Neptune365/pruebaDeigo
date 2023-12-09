package com.upao.biblioteca.controller;

import com.upao.biblioteca.domain.entity.Solicitud;
import com.upao.biblioteca.domain.service.LibroService;
import com.upao.biblioteca.domain.service.SolicitudService;
import com.upao.biblioteca.domain.service.UsuarioService;
import com.upao.biblioteca.infra.repository.LibroRepository;
import com.upao.biblioteca.infra.repository.SolicitudRepository;
import com.upao.biblioteca.infra.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/ver-solicitudes")
    public List<Solicitud> verSolicitudes(){
        return solicitudRepository.findAll();
    }

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarSolicitud(@RequestParam Long usuarioId, @RequestParam Long libroId) {
        try {
            Solicitud solicitud = solicitudService.realizarSolicitud(usuarioId, libroId);
            return new ResponseEntity<>(solicitud, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/realizarRecojo")
    public ResponseEntity<String> realizarRecojo(@RequestParam Long solicitudId) {
        try {
            solicitudService.realizarRecojo(solicitudId);
            return ResponseEntity.ok("Recojo realizada con éxito");
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/realizarDevolucion")
    public ResponseEntity<String> realizarDevolucion(@RequestParam Long solicitudId) {
        try {
            solicitudService.actualizarDevolucion(solicitudId);
            return ResponseEntity.ok("Devolución realizada con éxito");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}