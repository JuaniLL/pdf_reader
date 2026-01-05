package com.personal.pdf_reader.service;

import com.personal.pdf_reader.model.LineaFacturaPedidosYa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LineaFacturaParser {

    // Ejemplo de fila:
    // SERVICIOS PEDIDOSYA (Puppis - Caballito) 1.000 unidades 689468.05 -34103.21 655364.84 21.000 792991.45
    //
    // Grupos:
    // 1 -> descripción
    // 2 -> cantidad
    // 3 -> precio unit
    // 4 -> descuento
    // 5 -> subtotal
    // 6 -> alicuota
    // 7 -> subtotal con IVA
    private static final Pattern LINEA_FACTURA_PATTERN = Pattern.compile(
            "^(.+?)\\s+([0-9\\.,]+)\\s+unidades\\s+([0-9\\.,]+)\\s+(-?[0-9\\.,]+)\\s+([0-9\\.,]+)\\s+([0-9\\.,]+)\\s+([0-9\\.,]+)\\s*$"
    );

    public List<LineaFacturaPedidosYa> extraerLineas(String textoFactura) {
        List<LineaFacturaPedidosYa> resultado = new ArrayList<>();

        String[] lineas = textoFactura.split("\\R"); // separa por saltos de línea

        for (String linea : lineas) {
            String trimmed = linea.trim();
            if (trimmed.isEmpty()) continue;

            Matcher matcher = LINEA_FACTURA_PATTERN.matcher(trimmed);
            if (matcher.matches()) {
                String producto = matcher.group(1).trim();
                String subtotal = matcher.group(5).trim();
                String subtotalConIva = matcher.group(7).trim();

                resultado.add(new LineaFacturaPedidosYa(producto, subtotal, subtotalConIva));
            }
        }

        return resultado;
    }
}
