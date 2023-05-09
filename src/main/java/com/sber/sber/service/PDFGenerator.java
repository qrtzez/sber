package com.sber.sber.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGenerator {

    public void exportPdf(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTittle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTittle.setSize(18);
        Paragraph paragraph1 = new Paragraph("Tittle", fontTittle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(14);
        Paragraph paragraph2 = new Paragraph("Paragraph", font);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph1);
        document.add(paragraph2);
        document.close();


    }
}
