package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value ="/product")
@Transactional
public class ProductContoller {

	@Autowired
	private final ProductRepository productRepository;

	ProductContoller(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}


	@GetMapping(path = "/inventory")
	@ResponseBody
	public List<String> getInventory() {
		return productRepository.getProductInventory("");
	}
	@GetMapping(path = "/preferredInventory")
	@ResponseBody
	public List<String> getPreferredInventory() {
		return productRepository.getProductPreferredInventory("");
	}


}
