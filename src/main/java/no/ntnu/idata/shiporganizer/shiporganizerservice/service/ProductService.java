package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * The Product service. Gets data from the Product repository modifies it and passes it to the controller
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MailService mailService;


    /**
     * Instantiates a new Product service.
     *
     * @param productRepository the product repository
     * @param mailService
     */
    public ProductService(ProductRepository productRepository, MailService mailService) {
        this.productRepository = productRepository;
        this.mailService = mailService;
    }

    /**
     * Gets initial product inventory.
     *
     * @param department the users department
     * @return the product inventory
     */
    public List<Product> getInitialProductInventory(String department) {
        return productRepository.getInitialProductInventory(department);
    }

    /**
     * Gets updated product inventory.
     *
     * @param department the users department
     * @param date
     * @return the product inventory
     */
    public List<Product> getUpdatedProductInventory(String department, String date) {
        return productRepository.getUpdatedProductInventory(department, date);
    }


    /**
     * Gets product preferred inventory.
     *
     * @param department the users department
     * @return the product preferred inventory
     */
    public List<Product> getProductRecommendedInventory(String department) {
        return productRepository.getProductRecommendedInventory(department);
    }


    /**
     * Increases or decreases the stock for a specific product.
     *
     * @param productNo the product number
     * @param username  the username
     * @param quantity  the quantity
     * @param longitude the longitude
     * @param latitude  the latitude
     * @return Successful if update is successful
     */
    public String setNewStock(String productNo, String username, int quantity, float longitude, float latitude, String date) {
        int result = productRepository.setNewStock(productNo, username, quantity, longitude, latitude, date);
        if(result == 1) {
            return "Success";
        }
        return "";
    }

    public boolean createNewProduct(String productName, String productNumber, int desiredStock, int stock, String barcode, String department,String dateTime) {
        boolean success = false;
        int successInt = productRepository.createNewProduct(productName, productNumber, desiredStock, stock, barcode, department,dateTime);
        if(successInt == 1) {
            success = true;
        }

        return success;
    }


    public boolean checkProdNumber(String productNumber){
        boolean success = false;
        List<Product> products = productRepository.getAll(productNumber);

        if(!products.isEmpty()){
            success=true;
        }
        return success;
    }

    public boolean deleteProduct(String productNumber) {
        boolean success = false;

        int successInt = productRepository.deleteProduct(productNumber);

        if(successInt == 1) {
            success = true;
        }

        return success;
    }

    public boolean editProduct(String productName, String productNumber, int desiredStock, String barcode, String department,String dateTime) {
        boolean success = false;

        int successInt = productRepository.editProduct(productName, productNumber, desiredStock, barcode, department,dateTime);
        if(successInt == 1) {
            success = true;
        }
        return success;
    }

    /// Creates the pdf with correct content and calls the mailService to send the email with the pdf
    public void createPdf(List<Product> products, String email, String[] recipients,String department){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("BestillingsListe.pdf"));
            document.open();
            addMetaData(document);
            addTextToPdf(document,email,department);
            addTableToPdf(document,products);
            document.close();

            mailService.sendPdfEmail(email,recipients,"BestillingsListe.pdf");
        }catch (DocumentException | FileNotFoundException | MessagingException e ){
            System.out.println(e);
        }
    }
    // Add File meta data
    private static void addMetaData(Document document) {
        document.addTitle("Produkter for bestilling");
        document.addAuthor("Ship Organizer app");
        document.addCreator("Ship Organizer app");
    }
    // Add Text to pdf
    private static void addTextToPdf(Document document,String email, String department) throws DocumentException {
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
                "Department: " + department,
                font));
        preface.add(new Paragraph(
                "Dato: " + new Date(),
                font));
        addEmptyLine(preface, 3);
        document.add(preface);
    }
    // Add Table with products to pdf
    private static void addTableToPdf(Document document, List<Product> products) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        Stream.of("Produkt navn", "Produkt nummer", "Antall å bestille")
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
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
