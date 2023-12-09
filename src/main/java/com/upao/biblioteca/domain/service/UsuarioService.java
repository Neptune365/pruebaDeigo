package com.upao.biblioteca.domain.service;

import com.upao.biblioteca.domain.entity.Rol;
import com.upao.biblioteca.domain.entity.Usuario;
import com.upao.biblioteca.infra.repository.UsuarioRepository;
import com.upao.biblioteca.infra.security.JwtService;
import com.upao.biblioteca.infra.security.LoginRequest;
import com.upao.biblioteca.infra.security.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    public TokenResponse addUsuario(Usuario usuario) {
        Usuario user = Usuario.builder()
                .username(usuario.getUsername())
                .password(passwordEncoder.encode( usuario.getPassword()))
                .nombre(usuario.getNombre())
                .edad(usuario.getEdad())
                .telefono(usuario.getTelefono())
                .correo(usuario.getCorreo())
                .dni(usuario.getDni())
                .rol(Rol.LECTOR)
                .build();

        usuarioRepository.save(user);

        return TokenResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
