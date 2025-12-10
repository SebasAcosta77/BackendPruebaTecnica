package com.jdc.clinica.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventarioDTO {


    private Long idinventario;

  
    @NotBlank(message = "El NIT de la empresa es obligatorio")
    private String empresaNit;



    private Long productoId;

    private int stock;
    private Integer stockMinimo;

   
    private String estado;

    public Long getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(Long idinventario) {
        this.idinventario = idinventario;
    }

    public String getEmpresaNit() {
        return empresaNit;
    }

    public void setEmpresaNit(String empresaNit) {
        this.empresaNit = empresaNit;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
