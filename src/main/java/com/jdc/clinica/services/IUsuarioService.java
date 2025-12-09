package com.jdc.clinica.services;

import com.jdc.clinica.DTO.*;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ProductoCategoriaEntity;
import com.jdc.clinica.models.UsuariosEntity;

import java.util.List;

public interface IUsuarioService {
    UsuarioDTO crearUsuario(UsuarioCreateDTO dto);
    List<UsuarioListDTO> findAll();
    public UsuariosEntity findById(Long id);
    public UsuariosEntity save(UsuarioDTO usuarioDTO);
    public UsuariosEntity actualizarPorId(Long id, UsuarioDTO usuarioDTO);
    void delete(Long id);
}
