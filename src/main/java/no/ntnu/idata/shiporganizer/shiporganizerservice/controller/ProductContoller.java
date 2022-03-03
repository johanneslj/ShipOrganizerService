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
@RequestMapping(value = "/product")
@Transactional
public class ProductContoller {

	private final ProductService productService;

	/**
	 * Instantiates a new Product controller.
	 *
	 * @param productService the product service
	 */
	ProductContoller(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Gets inventory.
	 *
	 * @return the inventory
	 */
	@PostMapping(path = "/inventory")
	public List<Product> getInventory(HttpEntity<String> http) {
		List<Product> products = new ArrayList<>();
		try {
			JSONObject json = new JSONObject(http.getBody());
			String department = json.getString("department");
			products = productService.getProductInventory(department);

		} catch (JSONException e) {

		}
		return products;
	}

	/**
	 * Gets preferred inventory.
	 *
	 * @return the preferred inventory
	 */
	@PostMapping(path = "/RecommendedInventory")
	public List<Product> getRecommendedInventory(HttpEntity<String> http) {
		List<Product> products = new ArrayList<>();
		try {
			JSONObject json = new JSONObject(http.getBody());
			String department = json.getString("department");
			products = productService.getProductRecommendedInventory(department);
		} catch (JSONException e) {

		}
		return products;
	}

	/**
	 * Set new stock
	 *
	 * @param http the request body
	 * @return 200 OK or 204 No content
	 */
	@PostMapping(path = "/setNewStock")
	public ResponseEntity setNewStock(HttpEntity<String> http) {
		String Success = "";
		try {
			JSONObject json = new JSONObject(http.getBody());

			String productNumber = json.optString("productnumber");
			String username = json.getString("username");
			int quantity = json.optInt("quantity");
			float latitude = parseFloat(json.optString("latitude"));
			float longitude = parseFloat(json.optString("longitude"));

			Success = productService.setNewStock(productNumber, username, quantity, longitude, latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Success.equals("Success")) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}


}
