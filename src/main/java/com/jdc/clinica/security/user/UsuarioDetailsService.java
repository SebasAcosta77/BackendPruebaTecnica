package com.jdc.clinica.security.user;

import com.jdc.clinica.models.UsuariosEntity;
import com.jdc.clinica.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UsuarioDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepo;

    public UsuarioDetailsService(IUsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuariosEntity usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new UsuarioDetails(usuario);
    }
}
