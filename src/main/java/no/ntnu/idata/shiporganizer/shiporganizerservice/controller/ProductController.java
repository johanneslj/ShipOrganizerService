package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

	/**
	 * Instantiates a new Product controller.
	 *
	 * @param productService the product service
	 */
	ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Gets inventory.
	 *
	 * @return the inventory
	 */
	@PostMapping(path = "/inventory")
	public ResponseEntity<List<Product>> getInventory(HttpEntity<String> http) {
		try {
			JSONObject json = new JSONObject(http.getBody());
			String department = json.getString("department");
			List<Product> products = productService.getProductInventory(department);

			return ResponseEntity.ok(products);
		} catch (JSONException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Gets preferred inventory.
	 *
	 * @return the preferred inventory
	 */
	@PostMapping(path = "/RecommendedInventory")
	public ResponseEntity<List<Product>> getRecommendedInventory(HttpEntity<String> http) {
		try {
			JSONObject json = new JSONObject(http.getBody());
			String department = json.getString("department");

			List<Product> products = productService.getProductRecommendedInventory(department);

			return ResponseEntity.ok(products);
		} catch (JSONException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Set new stock
	 *
	 * @param http the request body
	 * @return 200 OK or 204 No content
	 */
	@PostMapping(path = "/setNewStock")
	public ResponseEntity<String> setNewStock(HttpEntity<String> http) {
		String Success = "";
		try {
			JSONObject json = new JSONObject(http.getBody());

			String productNumber = json.optString("productNumber");
			String username = json.getString("username");
			int quantity = json.optInt("quantity");
			float latitude = parseFloat(json.optString("latitude"));
			float longitude = parseFloat(json.optString("longitude"));

			Success = productService.setNewStock(productNumber, username, quantity, longitude,
					latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Success.equals("Success")) {
			return ResponseEntity.ok("success");
		}
		return ResponseEntity.badRequest().build();
	}
}
