package com.jdc.clinica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmpresaDTO {

    @NotBlank(message = "El NIT de la empresa es obligatorio")
    private String nit;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150, message = "El nombre de la empresa no debe superar los 150 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección de la empresa es obligatoria")
    @Size(max = 200, message = "La dirección no debe superar los 200 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono de la empresa es obligatorio")
    private String telefono;

    private String estado = "ACTIVO";

    public EmpresaDTO() {} // NECESARIO PARA FRAMEWORKS

    public EmpresaDTO(String nit, String nombre, String direccion, String telefono,  String estado) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
