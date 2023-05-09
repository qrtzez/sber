package com.sber.sber.controller;

import com.sber.sber.service.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/print_form")
public class PrintFormController {

    @Autowired
    private PDFGenerator pdfGenerator;

    @GetMapping("/pdf")
    public void pdfGenerate(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_.pdf";
        response.setHeader(headerKey, headerValue);

        pdfGenerator.exportPdf(response);
    }
}
