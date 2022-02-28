package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.OrderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	@PostMapping (path = "/new")
	public ResponseEntity<String> insertNewOrder(HttpEntity<String> http) {
		String success = "";
		try{
			JSONObject json = new JSONObject(http.getBody());

			String imagename = json.optString("imageName");
			String department = json.getString("department");
			success = orderService.insertNewOrder(department,imagename);
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
			success = orderService.updateOrder(department,imagename);
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
	public ResponseEntity<List<Orders>> getPendingOrders() {
		return ResponseEntity.ok(orderService.getPendingOrders(""));
	}

	/**
	 * Gets confirmed orders.
	 *
	 * @return the confirmed orders
	 */
	@GetMapping(path = "/user/pending")
	@ResponseBody
	public ResponseEntity<List<Orders>> getUserPendingOrders() {
		return ResponseEntity.ok(orderService.getPendingOrders("Deck"));
	}

	/**
	 * Gets confirmed orders.
	 *
	 * @return the confirmed orders
	 */
	@GetMapping(path = "/confirmed")
	@ResponseBody
	public ResponseEntity<List<Orders>> getAdminConfirmedOrders() {
		return ResponseEntity.ok(orderService.getConfirmedOrders(""));
	}



}
