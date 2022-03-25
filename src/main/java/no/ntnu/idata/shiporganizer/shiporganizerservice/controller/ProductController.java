package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import static java.lang.Float.parseFloat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Product controller.
 */
@RestController
@RequestMapping(value = "/api/product")
@Transactional
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Gets Initial inventory.
     *
     * @return the inventory
     */
    @PostMapping(path = "/get-inventory")
    public List<Product> getInventory(HttpEntity<String> entity) {
        List<Product> products = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(entity.getBody());
            String department = json.getString("department");
            products = productService.getInitialProductInventory(department);
        } catch (JSONException ignored) {
        }
        return products;
    }

    /**
     * Gets updated inventory.
     *
     * @return the last updated inventory
     */
    @PostMapping(path = "/recently-updated-inventory")
    public List<Product> getUpdatedInventory(HttpEntity<String> entity) {
        List<Product> UpdatedProducts = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(entity.getBody());
            String department = json.getString("department");
            Date date = new Date(json.getString("date"));
            UpdatedProducts = productService.getUpdatedProductInventory(department, date);

        } catch (JSONException ignored) {
        }
        return UpdatedProducts;
    }

    /**
     * Gets preferred inventory.
     *
     * @return the preferred inventory
     */
    @PostMapping(path = "/get-recommended-inventory")
    public List<Product> getRecommendedInventory(HttpEntity<String> entity) {
        List<Product> products = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(entity.getBody());
            String department = json.getString("department");
            products = productService.getProductRecommendedInventory(department);
        } catch (JSONException ignored) {
        }
        return products;
    }

    /**
     * Creates a new product to add to the database
     * The new product has a product name, product number, current stock,
     * a department and potentially a barcode
     *
     * @param entity The data coming from frontend
     * @return response bad request if failed and ok if succeeded
     */
    @PostMapping(path = "/new-product")
    public ResponseEntity<String> createNewProduct(HttpEntity<String> entity) {
        try {
            JSONObject json = new JSONObject(entity.getBody());
            String productName = json.getString("productName");
            int productNumber = Integer.parseInt(json.getString("productNumber"));
            int stock = Integer.parseInt(json.getString("stock"));
            String barcode = json.getString("barcode");
            String department = json.getString("department");

            boolean success = productService.createNewProduct(productName, productNumber, stock, barcode, department);
            if(success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }

        } catch (JSONException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Edits an already existing product
     * Can edit both name and barcode of the barcode
     *
     * @param entity The details which allow a product to be modified
     * @return bad request if failed and ok if succeeded
     */
    @PostMapping(path = "/edit-product")
    public ResponseEntity<String> editProduct(HttpEntity<String> entity) {
        try {
            JSONObject json = new JSONObject(entity.getBody());
            String productName = json.getString("productName");
            int productNumber = Integer.parseInt(json.getString("productNumber"));
            String barcode = json.getString("barcode");
            String department = json.getString("department");

            boolean success = productService.editProduct(productName, productNumber, barcode, department);
            if(success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }

        } catch (JSONException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Set new stock
     *
     * @param entity the request body
     * @return 200 OK or 204 No content
     */
    @PostMapping(path = "/set-new-stock")
    public ResponseEntity<String> setNewStock(HttpEntity<String> entity) {
        try {
            JSONObject json = new JSONObject(entity.getBody());

            setNewStockFromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    private void setNewStockFromJson(JSONObject json) throws JSONException {
        productService.setNewStock(
            json.optString("productnumber"),
            json.getString("username"),
            json.optInt("quantity"),
            parseFloat(json.optString("longitude")),
            parseFloat(json.optString("latitude")));
    }

}
