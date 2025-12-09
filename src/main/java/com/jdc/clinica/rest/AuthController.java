package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.LoginDTO;
import com.jdc.clinica.DTO.UsuarioCreateDTO;
import com.jdc.clinica.models.UsuariosEntity;
import com.jdc.clinica.security.jwt.JwtService;
import com.jdc.clinica.security.user.UsuarioDetails;
import com.jdc.clinica.services.Implemets.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;


    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AuthService authService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    // =====================================================
    // LOGIN
    // =====================================================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();

        Long id = userDetails.getId();
        String email = userDetails.getUsername();
        String rol = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow()
                .getAuthority()
                .replace("ROLE_", "");

        String token = jwtService.generateToken(id, email, rol);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", id);
        response.put("email", email);
        response.put("rol", rol);

        return ResponseEntity.ok(response);
    }

    // =====================================================
    // REGISTRO
    // =====================================================
    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody UsuarioCreateDTO dto) {

        UsuariosEntity usuario = authService.registrarUsuarioConCliente(dto);

        return ResponseEntity.ok(
                "Usuario y cliente creados — ID: " + usuario.getIduser()
        );
    }
}
