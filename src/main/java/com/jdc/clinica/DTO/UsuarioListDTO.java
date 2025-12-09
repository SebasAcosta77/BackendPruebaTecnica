package com.jdc.clinica.DTO;

import com.jdc.clinica.models.UsuariosEntity;

public class UsuarioListDTO {
    private Long iduser;
    private String email;
    private UsuariosEntity.Rol rol;

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuariosEntity.Rol getRol() {
        return rol;
    }

    public void setRol(UsuariosEntity.Rol rol) {
        this.rol = rol;
    }
}
