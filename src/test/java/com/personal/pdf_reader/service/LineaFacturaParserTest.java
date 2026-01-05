package com.personal.pdf_reader.service;

import com.personal.pdf_reader.model.LineaFacturaPedidosYa;
import com.personal.pdf_reader.model.PdfReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LineaFacturaParserTest {

    @Autowired
    private PdfReader pdfReader;

    @Autowired
    private LineaFacturaParser lineaFacturaParser;

    @Test
    void extraerLineasDeFactura() throws Exception {
        String texto = pdfReader.extraerTextoDesdeResources("facturas/example_pedidosya.pdf");

        List<LineaFacturaPedidosYa> lineas = lineaFacturaParser.extraerLineas(texto);

        System.out.println("\n===== LINEAS DE FACTURA =====");
        for (LineaFacturaPedidosYa l : lineas) {
            System.out.println(l);
        }
        System.out.println("=============================\n");

        // Ejemplo de chequeos simples
        // (ajustá a valores que sepas que están en tu PDF)
        // assertFalse(lineas.isEmpty());
        // assertTrue(lineas.stream().anyMatch(l -> l.getProducto().contains("Puppis - Caballito")));
    }
}
