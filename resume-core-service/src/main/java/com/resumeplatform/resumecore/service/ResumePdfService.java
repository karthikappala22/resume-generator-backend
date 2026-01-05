package com.resumeplatform.resumecore.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.resumeplatform.resumecore.dto.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ResumePdfService {

    public byte[] generatePdf(ResumeResponse resume) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // ---------- TITLE ----------
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph(resume.getTitle(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(Chunk.NEWLINE);

        // ---------- EDUCATION ----------
        addSection(document, "Education");
        for (EducationResponse edu : resume.getEducations()) {
            document.add(new Paragraph(
                    edu.getDegree() + " - " + edu.getInstitution()
                            + " (" + edu.getStartYear() + " - " + edu.getEndYear() + ")"
            ));
        }

        document.add(Chunk.NEWLINE);

        // ---------- EXPERIENCE ----------
        addSection(document, "Experience");
        for (ExperienceResponse exp : resume.getExperiences()) {

            document.add(new Paragraph(
                    exp.getRole() + " | " + exp.getCompanyName()
            ));

            com.lowagie.text.List bulletList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            for (String bullet : exp.getBullets()) {
                bulletList.add(new ListItem(bullet));
            }

            document.add(bulletList);
            document.add(Chunk.NEWLINE);
        }

        // ---------- SKILLS ----------
        addSection(document, "Skills");
        document.add(new Paragraph(String.join(", ", resume.getSkills())));

        document.add(Chunk.NEWLINE);

        // ---------- CERTIFICATIONS ----------
        addSection(document, "Certifications");
        for (CertificationResponse cert : resume.getCertifications()) {
            document.add(new Paragraph(
                    cert.getName() + " - " + cert.getIssuer()
            ));
        }

        document.close();

        return outputStream.toByteArray();
    }

    private void addSection(Document document, String title)
            throws DocumentException {

        Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        document.add(new Paragraph(title, sectionFont));
        document.add(new Paragraph("--------------------------------------"));
    }
}
