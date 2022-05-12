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
     * @param mailService the MailService
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
     * @param date the current date and time send from the client
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
     * @param date      the current date and time send from the client
     * @return Successful if update is successful
     */
    public String setNewStock(String productNo, String username, int quantity, float longitude, float latitude, String date) {
        int result = productRepository.setNewStock(productNo, username, quantity, longitude, latitude, date);
        if(result == 1) {
            return "Success";
        }
        return "";
    }

    /**
     * Creates a new product
     * @param productName   the new products name
     * @param productNumber the new products number
     * @param desiredStock  the new products desired stock
     * @param stock         the new products stock
     * @param barcode       the new products barcode
     * @param department    the department the product show under
     * @param dateTime      the current date and time send from the client
     * @return boolean True if the record is inserted and false if not
     */
    public boolean createNewProduct(String productName, String productNumber, int desiredStock, int stock, String barcode, String department,String dateTime) {
        boolean success = false;
        int successInt = productRepository.createNewProduct(productName, productNumber, desiredStock, stock, barcode, department,dateTime);
        if(successInt == 1) {
            success = true;
        }

        return success;
    }

    /**
     * Checks if a product number already exists
     * @param productNumber the product number to check
     * @return boolean True if the product number exists and false if not
     */
    public boolean checkProdNumber(String productNumber){
        boolean success = false;
        List<Product> products = productRepository.getAll(productNumber);

        if(!products.isEmpty()){
            success=true;
        }
        return success;
    }

    /**
     * Deletes a product
     * @param productNumber the product number of the product to be deleted
     * @return boolean True if the product is deleted and false if not
     */
    public boolean deleteProduct(String productNumber) {
        boolean success = false;

        int successInt = productRepository.deleteProduct(productNumber);

        if(successInt == 1) {
            success = true;
        }

        return success;
    }

    /**
     * Updated the information on a product
     * @param id            the updated products id
     * @param productName   the updated products name
     * @param productNumber the updated products number
     * @param desiredStock  the updated products desired stock
     * @param barcode       the updated products barcode
     * @param department    the department the product show under
     * @param dateTime      the current date and time send from the client
     * @return boolean True if the record is updated and false if not
     */
    public boolean editProduct(int id,String productName, String productNumber, int desiredStock, String barcode, String department,String dateTime) {
        boolean success = false;

        int successInt = productRepository.editProduct(id,productName, productNumber, desiredStock, barcode, department,dateTime);
        if(successInt == 1) {
            success = true;
        }
        return success;
    }

    /**
     * Creates the pdf with correct content and calls the mailService to send the email with the pdf
     * @param products List of products to be orders
     * @param email the user who generated the reports email
     * @param recipients List of recipients to receive the email
     * @param department the users department
     */
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
        }catch (DocumentException | FileNotFoundException e ){
            System.out.println(e);
        }
    }

    /**
     * Adds meta data to the pdf document
     * @param document The pdf document
     */
    private static void addMetaData(Document document) {
        document.addTitle("Produkter for bestilling");
        document.addAuthor("Ship Organizer app");
        document.addCreator("Ship Organizer app");
    }
    /**
     * Adds the main text to the pdf document
     * @param document The pdf document
     * @param email The users email
     * @param department The users department
     */
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

    /**
     * Adds Table with products to pdf document
     * @param document The pdf document
     * @param products List of the products to be ordered
     */
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

    /**
     * Adds an empty line to the pdf
     * @param paragraph the paragraph to add the spaces
     * @param number number of spaces to add
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
