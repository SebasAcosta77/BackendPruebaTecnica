package com.jdc.clinica.DTO;

import java.util.List;

public class CrearOrdenDTO {

    private List<OrdenProductoDTO> productos;

    public List<OrdenProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<OrdenProductoDTO> productos) {
        this.productos = productos;
    }
}
