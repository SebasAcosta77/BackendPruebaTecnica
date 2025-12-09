package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="categorias")
public class CategoriaEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategoria")
    private Long idcategoria;

    @NotNull
    @Column(name = "nombre", length = 80)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", length = 100)
    private String descripcion;


    // RELACIÓN N:N CON PRODUCTO

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductoCategoriaEntity> productos;


    public CategoriaEntity() {
    }


    public CategoriaEntity(Long idcategoria, String nombre, String descripcion) {
        this.idcategoria = idcategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Long idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ProductoCategoriaEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCategoriaEntity> productos) {
        this.productos = productos;
    }
}
