package com.personal.pdf_reader.service;

import com.personal.pdf_reader.model.LineaFacturaPedidosYa;
import com.personal.pdf_reader.model.PdfReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExcelExporterTest {

    @Autowired
    private PdfReader pdfReader;

    @Autowired
    private LineaFacturaParser lineaFacturaParser;

    @Autowired
    private ExcelExporter excelExporter;

    @Test
    void exportarLineasAFicheroXlsx() throws Exception {
        // 1) Leo el texto del PDF
        String texto = pdfReader.extraerTextoDesdeResources("facturas/example_pedidosya.pdf");

        // 2) Parseo las líneas de la tabla
        List<LineaFacturaPedidosYa> lineas = lineaFacturaParser.extraerLineas(texto);

        // 3) Armo una ruta de salida, por ejemplo en target/
        Path destino = Path.of("src/main/resources/tablas", "lineas_factura.xlsx");

        // 4) Exporto a Excel
        excelExporter.exportarLineasAFichero(lineas, destino);

        System.out.println("Archivo generado en: " + destino.toAbsolutePath());
    }
}