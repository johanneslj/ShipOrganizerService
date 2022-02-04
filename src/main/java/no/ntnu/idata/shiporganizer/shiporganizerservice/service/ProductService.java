package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The Product service. Gets data from the Product repository modifies it and passes it to the controller
 */
@Service
public class ProductService {
	@Autowired
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
	 * Gets product inventory.
	 *
	 * @param dep the users department
	 * @return the product inventory
	 */
	public List<Product> getProductInventory(String dep) {
		List<Product> products = new ArrayList<>();
		List<String> bits = productRepository.getProductInventory(dep);

		for (String ting : bits) {
			String[] data = ting.split(",");
			products.add(new Product(data[0], data[1], data[2], data[3]));
		}
		return products;
	}

	/**
	 * Gets product preferred inventory.
	 *
	 * @param dep the users department
	 * @return the product preferred inventory
	 */
	public List<Product> getProductPreferredInventory(String dep) {
		List<Product> products = new ArrayList<>();
		List<String> bits = productRepository.getProductPreferredInventory(dep);

		for (String ting : bits) {
			String[] data = ting.split(",");
			products.add(new Product(data[0], data[1], data[2], data[3]));
		}
		return products;
	}

}
