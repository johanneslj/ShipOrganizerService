package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * The Product service. Gets data from the Product repository modifies it and passes it to the controller
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Instantiates a new Product service.
     *
     * @param productRepository the product repository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public boolean createNewProduct(String productName, int productNumber, int desiredStock, int stock, String barcode, String department) {
        boolean success = false;

        int successInt = productRepository.createNewProduct(productName, productNumber, desiredStock, stock, barcode, department);

        if(successInt == 1) {
            success = true;
        }

        return success;
    }

    public boolean editProduct(String productName, int productNumber, int desiredStock, String barcode, String department) {
        boolean success = false;

        int successInt = productRepository.editProduct(productName, productNumber, desiredStock, barcode, department);
        if(successInt == 1) {
            success = true;
        }
        return success;
    }

}
