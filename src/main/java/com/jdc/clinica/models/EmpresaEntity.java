package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name ="empresas")
public class EmpresaEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nit", length = 20)
    private String nit;

    @NotNull
    @Column(name = "nombre", length = 100)
    private String nombre;

    @NotNull
    @Column(name = "direccion", length = 150)
    private String direccion;

    @NotNull
    @Column(name = "telefono", length = 300)
    private String telefono;

    @Column(name = "estado", length = 20, nullable = false)
    private String estado = "ACTIVO"; // Valor por defecto

    //
    public EmpresaEntity() {
    }


    public EmpresaEntity(String nit, String nombre, String direccion, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

  
    // RELACIÓN 1:N PRODUCTOS
   
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<ProductoEntity> productos;

  


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

    public List<ProductoEntity> getProductos() {
        return productos;
    }
    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }
}
