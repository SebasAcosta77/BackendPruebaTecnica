package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.UsuarioCreateDTO;
import com.jdc.clinica.models.ClienteEntity;
import com.jdc.clinica.models.UsuariosEntity;
import com.jdc.clinica.repository.IClienteRepository;
import com.jdc.clinica.repository.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final IUsuarioRepository usuariosRepository;
    private final IClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    //
    public AuthService(
            IUsuarioRepository usuariosRepository,
            IClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuariosRepository = usuariosRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

 
    // REGISTRO 
 
    @Transactional
    public UsuariosEntity registrarUsuarioConCliente(UsuarioCreateDTO dto) {


        if (usuariosRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(" El correo ya está registrado: " + dto.getEmail());
        }


        String rolStr = dto.getRol().toUpperCase();
        UsuariosEntity.Rol rol;
        try {
            rol = UsuariosEntity.Rol.valueOf(rolStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(" Rol inválido. Valores permitidos: ADMIN, EXTERNO");
        }

        //  Crear usuario con contraseña encriptada
        UsuariosEntity usuario = new UsuariosEntity();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(rol);

        usuario = usuariosRepository.save(usuario);

     
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUsuario(usuario);

        clienteRepository.save(cliente);

        return usuario;
    }
}
