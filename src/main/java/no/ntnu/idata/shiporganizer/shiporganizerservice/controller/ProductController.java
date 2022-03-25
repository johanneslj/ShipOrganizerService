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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;


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
			String date = json.getString("DateTime");
			UpdatedProducts = productService.getUpdatedProductInventory(department,date);

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
        parseFloat(json.optString("latitude")),
			json.getString("datetime"));
  }

}
