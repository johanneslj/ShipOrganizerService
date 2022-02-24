package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Product contoller.
 */
@RestController
@RequestMapping(value ="/product")
@Transactional
public class ProductContoller {

	private ProductService productService;

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
	 * @param requestBody the request body
	 * @return 200 OK or 204 No content
	 */
	@PostMapping(path = "/setNewStock")
	public ResponseEntity setNewStock(@RequestBody String requestBody){
		if(productService.setNewStock(requestBody).equals("Success")){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

}
