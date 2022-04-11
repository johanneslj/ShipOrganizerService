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
import java.util.Date;
import java.util.List;
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
}
