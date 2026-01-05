package com.personal.pdf_reader.service;

import com.personal.pdf_reader.model.LineaFacturaPedidosYa;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class ExcelExporter {

    public byte[] exportarLineasABytes(List<LineaFacturaPedidosYa> lineas) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Factura");

            // Estilo header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            String[] headers = { "Producto / Servicio", "Subtotal", "Subtotal c/IVA" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (LineaFacturaPedidosYa linea : lineas) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(linea.getProducto());
                row.createCell(1).setCellValue(linea.getSubtotal());
                row.createCell(2).setCellValue(linea.getSubtotalConIva());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            return bos.toByteArray();
        }
    }
    public void exportarLineasAFichero(List<LineaFacturaPedidosYa> lineas, Path destino) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Factura");

            // Estilo simple para el header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Fila de encabezados
            Row headerRow = sheet.createRow(0);

            Cell h0 = headerRow.createCell(0);
            h0.setCellValue("Producto / Servicio");
            h0.setCellStyle(headerStyle);

            Cell h1 = headerRow.createCell(1);
            h1.setCellValue("Subtotal");
            h1.setCellStyle(headerStyle);

            Cell h2 = headerRow.createCell(2);
            h2.setCellValue("Subtotal c/IVA");
            h2.setCellStyle(headerStyle);

            // Filas de datos
            int rowIdx = 1;
            for (LineaFacturaPedidosYa linea : lineas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(linea.getProducto());
                row.createCell(1).setCellValue(linea.getSubtotal());
                row.createCell(2).setCellValue(linea.getSubtotalConIva());
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir al fichero
            try (FileOutputStream fos = new FileOutputStream(destino.toFile())) {
                workbook.write(fos);
            }
        }
    }
}
