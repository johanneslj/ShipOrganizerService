package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import static java.lang.Float.parseFloat;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.MailService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;


/**
 * The type Product controller.
 */
@RestController
@RequestMapping(value = "/api/product")
@Transactional
public class ProductController {

	private final ProductService productService;

	/**
	 * Instantiates a new Product contoller.
	 *
	 * @param productService the product service
	 */
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
			int desiredStock = Integer.parseInt(json.getString("desiredStock"));
			String barcode = json.getString("barcode");
			String department = json.getString("department");


			boolean success = productService.createNewProduct(productName, productNumber, desiredStock, stock, barcode, department);
			if (success) {
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
			int desiredStock = Integer.parseInt(json.getString("desiredStock"));
			String barcode = json.getString("barcode");
			String department = json.getString("department");

			boolean success = productService.editProduct(productName, productNumber, desiredStock, barcode, department);
			if (success) {
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
				json.optString("productNumber"),
				json.getString("username"),
				json.optInt("quantity"),
				parseFloat(json.optString("longitude")),
				parseFloat(json.optString("latitude")),
				json.getString("datetime"));
	}

	/**
	 * Gets list of products and recipients from the frontend and sends it to the
	 * productService to create the pdf
	 * @param entity Http request
	 */
	@PostMapping(path = "/create-pdf")
	public void createPdf(HttpEntity<String> entity) {
		try {
			String email = "";
			JSONObject json = new JSONObject(entity.getBody());
			List<Product> products = new ArrayList<>();
			String[] recipients = new String[0];

			JSONArray jsonArrayProducts = json.getJSONArray("items");
			for (int i = 0; i < jsonArrayProducts.length(); i++) {
				JSONObject product = jsonArrayProducts.getJSONObject(i);
				if(product.getInt("stock") > 0){
					products.add(new Product(product.getString("productName"), product.getString("productNumber"), product.getString("barcode"), ("" + product.getInt("stock"))));
				}
			}

			JSONArray jsonArrayRecipients = json.getJSONArray("receivers");
			if(jsonArrayRecipients.length()>1){
				recipients = new String[jsonArrayRecipients.length()-1];
				for (int i = 0; i < jsonArrayRecipients.length(); i++) {
					String receiver = jsonArrayRecipients.getString(i);
					if(i==0){
						email = receiver;
					}
					else {
						recipients[i-1] = receiver;
					}
				}
			}
			else{
				email = jsonArrayRecipients.getString(0);
			}
			
			productService.createPdf(products, email, recipients);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}
}
