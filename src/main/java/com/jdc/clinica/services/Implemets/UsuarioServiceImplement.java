package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.UsuarioCreateDTO;
import com.jdc.clinica.DTO.UsuarioDTO;
import com.jdc.clinica.DTO.UsuarioListDTO;
import com.jdc.clinica.models.UsuariosEntity;
import com.jdc.clinica.repository.IUsuarioRepository;
import com.jdc.clinica.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

  
    // CREAR USUARIO
  
    @Override
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {


        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new RuntimeException("El email no puede estar vacío");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña no puede estar vacía");
        }
        if (dto.getRol() == null || dto.getRol().isEmpty()) {
            throw new RuntimeException("El rol no puede estar vacío");
        }


        UsuariosEntity entity = new UsuariosEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));


        try {
            entity.setRol(UsuariosEntity.Rol.valueOf(dto.getRol().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido. Debe ser: ADMIN o EXTERNO");
        }


        UsuariosEntity saved = usuarioRepository.save(entity);


        UsuarioDTO response = new UsuarioDTO();
        response.setIduser(saved.getIduser());
        response.setEmail(saved.getEmail());
        response.setRol(saved.getRol());

        return response;
    }

   
    // LISTAR
  
    @Override
    public List<UsuarioListDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(user -> {
                    UsuarioListDTO dto = new UsuarioListDTO();
                    dto.setIduser(user.getIduser());
                    dto.setEmail(user.getEmail());
                    dto.setRol(user.getRol());
                    return dto;
                })
                .toList();
    }

   
    // BUSCAR
    
    @Override
    public UsuariosEntity findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

  
    // CREAR
  
    @Override
    public UsuariosEntity save(UsuarioDTO dto) {

        UsuariosEntity entity;


        if (dto.getIduser() != null) {
            entity = usuarioRepository.findById(dto.getIduser())
                    .orElseThrow(() ->
                            new RuntimeException("No existe usuario con ID: " + dto.getIduser()));
        } else {

            entity = new UsuariosEntity();
        }

        entity.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        entity.setRol(dto.getRol());

        return usuarioRepository.save(entity);
    }

    
    // ACTUALIZAR POR ID
  
    @Override
    public UsuariosEntity actualizarPorId(Long id, UsuarioDTO dto) {


        UsuariosEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));


        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            entity.setEmail(dto.getEmail());
        }


        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }


        if (dto.getRol() != null) {
            entity.setRol(dto.getRol());
        }


        return usuarioRepository.save(entity);
    }

    
    // ELIMINAR USUARIO
   
    @Override
    public void delete(Long id) {

        UsuariosEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ID: " + id));

        usuarioRepository.delete(entity);
    }
}
