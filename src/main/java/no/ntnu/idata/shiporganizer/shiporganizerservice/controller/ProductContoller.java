package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Float.parseFloat;


/**
 * The type Product contoller.
 */
@RestController
@RequestMapping(value ="/product")
@Transactional
public class ProductContoller {

	private final ProductService productService;

	/**
	 * Instantiates a new Product contoller.
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
	@GetMapping(path = "/inventory")
	@ResponseBody
	public List<Product> getInventory() {
		return productService.getProductInventory("");
	}

	/**
	 * Gets preferred inventory.
	 *
	 * @return the preferred inventory
	 */
	@GetMapping(path = "/RecommendedInventory")
	@ResponseBody
	public List<Product> getRecommendedInventory() {
		return productService.getProductRecommendedInventory("");
	}

	/**
	 * Set new stock
	 *
	 * @param http the request body
	 * @return 200 OK or 204 No content
	 */
	@PostMapping(path = "/setNewStock")
	public ResponseEntity setNewStock(HttpEntity<String> http){
		String Success = "";
		try{
			JSONObject json = new JSONObject(http.getBody());

			String productNumber = json.optString("productnumber");
			String username = json.getString("username");
			int quantity = json.optInt("quantity");
			float latitude = parseFloat(json.optString("latitude"));
			float longitude = parseFloat(json.optString("longitude"));

			Success = productService.setNewStock(productNumber,username,quantity,latitude,longitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Success.equals("Success")){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}



}
