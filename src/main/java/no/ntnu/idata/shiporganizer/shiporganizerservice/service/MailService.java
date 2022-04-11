package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    final private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /// Creates the pdf with correct content and calls the mailService to send the email with the pdf
    public void createAndSendPdf(List<Product> products, String email, String[] recipients) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("BestillingsListe.pdf"));
            document.open();
            addMetaData(document);
            addTextToPdf(document, email);
            addTableToPdf(document, products);
            document.close();

            sendPdfEmail(email, recipients, "BestillingsListe.pdf");
        } catch (DocumentException | FileNotFoundException | MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add File meta data
    private void addMetaData(Document document) {
        document.addTitle("Produkter for bestilling");
        document.addAuthor("Ship Organizer app");
        document.addCreator("Ship Organizer app");
    }

    // Add Text to pdf
    private void addTextToPdf(Document document, String email) throws DocumentException {
        Paragraph preface = new Paragraph();
        Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Produkter for bestilling", font));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(
            "Rapport generert av:",
            font));
        preface.add(new Paragraph(
            "Email: " + email,
            font));
        preface.add(new Paragraph(
            "Dato: " + new Date(),
            font));
        addEmptyLine(preface, 3);
        document.add(preface);
    }

    // Add Table with products to pdf
    private void addTableToPdf(Document document, List<Product> products) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        Stream.of("Produktnavn", "Produktnummer", "Antall å bestille")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
            });
        for (Product item : products) {
            table.addCell(item.getProductName());
            table.addCell(item.getProductNumber());
            table.addCell(item.getStock());
        }
        document.add(table);
    }

    // Adds an empty line to the pdf
    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public String sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("hansal@stud.ntnu.no");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        //javaMailSender.send(msg);

        return "ok";
    }

    public void sendNewPasswordVerificationCode(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject("Ship Organizer: Verification Code");
        msg.setText("Here is your verification code: " + code);

        javaMailSender.send(msg);
    }

    public void sendRegisteredEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject("Ship Organizer: You have been registered!");
        msg.setText("Your email has now been registered for the Ship Organizer app.\n" +
            "You can now set a new password to complete the registration.");

        javaMailSender.send(msg);
    }

    /// Sends the created PDF for all the products to order as an Email to a specific email
    public void sendPdfEmail(String userEmail, String[] recipients, String fileToAdd) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            FileSystemResource file = new FileSystemResource(new File(fileToAdd));
            MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(userEmail);
            helper.setCc(recipients);
            helper.setSubject("Ship Organizer: PDF for orders");
            helper.setText("There has been a order list sent from the Ship Organizer app.\n" +
                    "Please see attachment");
            helper.addAttachment("Order.pdf", file);

            javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }



}
