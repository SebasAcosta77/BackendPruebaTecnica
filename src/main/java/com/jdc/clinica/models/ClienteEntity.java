package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "clientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClienteEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long idcliente;

    @NotNull
    @Column(name = "nombre", length = 120)
    private String nombre;

    @NotNull
    @Column(name = "telefono", length = 30)
    private String telefono;

    // Relación con Usuario
    @OneToOne(optional = true)
    @JoinColumn(
            name = "iduser",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_cliente_usuario")
    )
    private UsuariosEntity usuario;

    // Relación 1:N con Ordenes
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenEntity> ordenes;

    public ClienteEntity() {
    }

    public ClienteEntity(Long idcliente, String nombre, String telefono, UsuariosEntity usuario, List<OrdenEntity> ordenes) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.usuario = usuario;
        this.ordenes = ordenes;
    }

    // Getters y setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public UsuariosEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosEntity usuario) {
        this.usuario = usuario;
    }

    public List<OrdenEntity> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenEntity> ordenes) {
        this.ordenes = ordenes;
    }
}
