package com.jdc.clinica.DTO;

import com.jdc.clinica.models.UsuariosEntity.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long iduser;
    private String email;
    private String password;
    private Rol rol;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
