package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ProductRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value ="/product")
@Transactional
public class ProductContoller {

	private ProductService productService;

	ProductContoller(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(path = "/inventory")
	@ResponseBody
	public List<Product> getInventory() {
		return productService.getProductInventory("");
	}
	@GetMapping(path = "/PreferredInventory")
	@ResponseBody
	public List<Product> getPreferredInventory() {
		return productService.getProductPreferredInventory("");
	}

	@PostMapping(path = "/setNewStock")
	public ResponseEntity setNewStock(@RequestBody String requestBody){
		if(productService.setNewStock(requestBody).equals("Success")){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

}
