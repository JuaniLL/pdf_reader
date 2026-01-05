package com.personal.pdf_reader.web;

import com.personal.pdf_reader.model.LineaFacturaPedidosYa;
import com.personal.pdf_reader.model.PdfReader;
import com.personal.pdf_reader.service.ExcelExporter;
import com.personal.pdf_reader.service.LineaFacturaParser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class FacturaController {

    private final PdfReader pdfReader;
    private final LineaFacturaParser lineaFacturaParser;
    private final ExcelExporter excelExporter;

    public FacturaController(PdfReader pdfReader,
                             LineaFacturaParser lineaFacturaParser,
                             ExcelExporter excelExporter) {
        this.pdfReader = pdfReader;
        this.lineaFacturaParser = lineaFacturaParser;
        this.excelExporter = excelExporter;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @PostMapping("/procesar")
    public ResponseEntity<ByteArrayResource> procesar(@RequestParam("file") MultipartFile file) throws Exception {

        // 1) Nombre original del PDF
        String originalName = file.getOriginalFilename();

        // fallback por si viene null
        if (originalName == null || originalName.isBlank()) {
            originalName = "factura.pdf";
        }

        // 2) Quitar extensión si existe
        String baseName = originalName;
        int dotIndex = originalName.lastIndexOf(".");
        if (dotIndex > 0) {
            baseName = originalName.substring(0, dotIndex);
        }

        // 3) Agregar la nueva extensión
        String nombreSalida = baseName + ".xlsx";

        // ===== Procesamiento =====

        String texto = pdfReader.extraerTextoDesdeStream(file.getInputStream());

        List<LineaFacturaPedidosYa> lineas = lineaFacturaParser.extraerLineas(texto);

        byte[] excelBytes = excelExporter.exportarLineasABytes(lineas);

        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ));
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nombreSalida + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}