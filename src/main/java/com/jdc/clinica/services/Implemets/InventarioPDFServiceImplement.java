package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.models.InventarioEntity;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InventarioPDFServiceImplement {

    public ByteArrayInputStream generarPDF(List<InventarioEntity> inventario) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

           
            // TÍTULO
           
            Font fontTitulo = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(33, 97, 140)); // azul elegante
            Paragraph titulo = new Paragraph(" Reporte de Inventario", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

           
            // FECHA DE CREACIÓN
           
            String fecha = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            Font fontFecha = new Font(Font.HELVETICA, 11, Font.ITALIC, new Color(100, 100, 100));
            Paragraph fechaCreacion = new Paragraph("Fecha de generación: " + fecha, fontFecha);
            fechaCreacion.setAlignment(Element.ALIGN_CENTER);
            fechaCreacion.setSpacingAfter(20);
            document.add(fechaCreacion);

        
            // TABLA
            
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            table.setWidths(new float[]{1.2f, 2.5f, 2.5f, 1.2f, 1.5f});

            // Encabezados 
            Font fontHeader = new Font(Font.HELVETICA, 12, Font.BOLD, Color.blue);

            agregarEncabezado(table, "ID", fontHeader);
            agregarEncabezado(table, "Empresa", fontHeader);
            agregarEncabezado(table, "Producto", fontHeader);
            agregarEncabezado(table, "Stock", fontHeader);
            agregarEncabezado(table, "Stock Mínimo", fontHeader);

            // Filas de datos
            Font fontFila = new Font(Font.HELVETICA, 11);

            for (InventarioEntity item : inventario) {
                agregarCelda(table, String.valueOf(item.getIdinventario()), fontFila);
                agregarCelda(table, item.getEmpresa().getNombre(), fontFila);
                agregarCelda(table, item.getProducto().getNombre(), fontFila);
                agregarCelda(table, String.valueOf(item.getStock()), fontFila);
                agregarCelda(table, String.valueOf(item.getStockMinimo()), fontFila);
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // CELDA NORMAL
  
    private void agregarCelda(PdfPTable table, String contenido, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido, font));
        cell.setPadding(6);
        cell.setBorderColor(new Color(200, 200, 200));
        table.addCell(cell);
    }

   
    // CELDA DE ENCABEZADO

    private void agregarEncabezado(PdfPTable table, String titulo, Font font) {
        PdfPCell header = new PdfPCell(new Phrase(titulo, font));
        header.setBackgroundColor(new Color(33, 97, 140)); // azul elegante
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.setPadding(8);
        header.setBorderColor(new Color(80, 80, 80));
        table.addCell(header);
    }
}
