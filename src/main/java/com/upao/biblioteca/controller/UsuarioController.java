package com.upao.biblioteca.controller;

import com.upao.biblioteca.domain.entity.Usuario;
import com.upao.biblioteca.domain.service.UsuarioService;
import com.upao.biblioteca.infra.security.LoginRequest;
import com.upao.biblioteca.infra.security.TokenResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador REST para operaciones relacionadas con usuarios.
 * Proporciona endpoints para el inicio de sesión y registro de usuarios.
 * Utiliza {@link UsuarioService} para realizar operaciones relacionadas con usuarios.
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;
    /**
     * Inicia sesión de un usuario y devuelve un token de autenticación.
     *
     * @param request Datos de inicio de sesión del usuario.
     * @return ResponseEntity con el token de autenticación.
     */
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }
    /**
     * Registra un nuevo usuario y devuelve un token de autenticación.
     *
     * @param usuario Datos del usuario a registrar.
     * @return ResponseEntity con el token de autenticación del nuevo usuario.
     */
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<TokenResponse> addUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.addUsuario(usuario));
    }
}
