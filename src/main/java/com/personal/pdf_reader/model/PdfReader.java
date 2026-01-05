package com.personal.pdf_reader.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfReader {

    /**
     * Lee un PDF que está en src/main/resources usando la ruta relativa dentro de resources.
     * Ej: "facturas/factura1.pdf"
     */
    public String extraerTextoDesdeResources(String rutaEnResources) throws IOException {
        ClassPathResource resource = new ClassPathResource(rutaEnResources);

        try (InputStream is = resource.getInputStream();
             PDDocument document = PDDocument.load(is)) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String extraerTextoDesdeStream(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}