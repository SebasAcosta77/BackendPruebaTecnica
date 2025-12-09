package com.jdc.clinica.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ClienteDTO {

    private Long idcliente;
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre del cliente no debe superar los 100 caracteres")
    private String nombre;
    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no debe superar los 150 caracteres")
    private String email;
    @NotBlank(message = "El teléfono del cliente es obligatorio")
    private String telefono;
    private List<Long> ordenesIds;



    public List<Long> getOrdenesIds() {
        return ordenesIds;
    }

    public void setOrdenesIds(List<Long> ordenesIds) {
        this.ordenesIds = ordenesIds;
    }

    public ClienteDTO() {}

    public ClienteDTO(Long idcliente, String nombre, String telefono) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
