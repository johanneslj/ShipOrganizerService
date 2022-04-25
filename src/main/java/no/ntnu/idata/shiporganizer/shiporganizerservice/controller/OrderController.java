package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.io.IOException;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Order;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.OrderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Order controller. Handles all the direct api calls and sends the variables to the service
 * for the correct handling
 */
@Controller
@RequestMapping(value ="/orders")
@Transactional
public class OrderController {

	/**
	 * The Order service.
	 */
	private final OrderService orderService;

	/**
	 * Instantiates a new Order controller.
	 *
	 * @param orderService the order service
	 */
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Inserts new order.
	 *
	 * @return 200 OK or 204 No content
	 */
	@PostMapping(path = "/new")
	public ResponseEntity<String> insertNewOrder(
			@RequestParam(value = "department") String department,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		try {
			System.out.println(department);
			System.out.println(image.getOriginalFilename());
			orderService.insertNewOrder(department, image);
		} catch (IOException e) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * Updates order from pending to confirmed
	 *
	 * @return 200 OK or 204 No content
	 */
	@PostMapping (path = "/update")
	public ResponseEntity<String> updateOrder(HttpEntity<String> http){
		String success = "";
		try{
			JSONObject json = new JSONObject(http.getBody());
			String imagename = json.optString("imageName");
			String department = json.getString("department");
			int status = json.getInt("status");
			success = orderService.updateOrder(department,imagename,status);
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		if(success.equals("Success")){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Gets pending orders.
	 *
	 * @return the pending orders
	 */
	@GetMapping(path = "/admin/pending")
	public ResponseEntity<List<Order>> getPendingOrders() {
		return ResponseEntity.ok(orderService.getPendingOrders(""));
	}

	/**
	 * Gets confirmed orders.
	 *
	 * @return the confirmed orders
	 */
	@PostMapping(path = "/user/pending")
	public ResponseEntity<List<Order>> getUserPendingOrders(HttpEntity<String> http) {
		List<Order> pendingOrders = new ArrayList<>();
		try {
			JSONObject json = new JSONObject(http.getBody());
			String department = json.getString("department");
			pendingOrders = orderService.getPendingOrders(department);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(pendingOrders);
	}

	/**
	 * Gets confirmed orders.
	 *
	 * @return the confirmed orders
	 */
	@GetMapping(path = "/confirmed")
	@ResponseBody
	public ResponseEntity<List<Order>> getAdminConfirmedOrders() {
		return ResponseEntity.ok(orderService.getConfirmedOrders(""));
	}



}
