package com.personal.pdf_reader.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PdfReaderTest {

    @Autowired
    private PdfReader pdfReader;

    @Test
    void leerPdfDesdeResources() throws Exception {
        // Ruta relativa dentro de src/main/resources
        String texto = pdfReader.extraerTextoDesdeResources("facturas/example_pedidosya.pdf");

        System.out.println("\n----- CONTENIDO DEL PDF -----\n");
        System.out.println(texto);
        System.out.println("\n-----------------------------\n");

        // Asserts básicos para validar que leyó algo
        assertNotNull(texto);
        assertFalse(texto.isBlank());

        // Si sabés alguna palabra que sí o sí está en la factura:
        // assertTrue(texto.contains("FACTURA"));
        // assertTrue(texto.contains("TOTAL"));
    }
}