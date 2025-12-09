package com.jdc.clinica.security.user;

import com.jdc.clinica.models.UsuariosEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UsuarioDetails implements UserDetails {

    private final UsuariosEntity usuario;

    public UsuarioDetails(UsuariosEntity usuario) {
        this.usuario = usuario;
    }


    // ⬇⬇⬇ IMPORTANTE — ESTE MÉTODO ES NUEVO
    public Long getId() {
        return usuario.getIduser();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // email como username
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
