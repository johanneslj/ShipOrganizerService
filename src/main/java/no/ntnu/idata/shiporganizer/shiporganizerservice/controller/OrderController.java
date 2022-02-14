package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.OrderService;
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
	public ResponseEntity<String> insertNewOrder(@RequestBody String requestBody) {
		if(orderService.insertNewOrder(requestBody).equals("Success")){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Gets pending orders.
	 *
	 * @return the pending orders
	 */
	@GetMapping(path = "/pending")
	@ResponseBody
	public List<Orders> getPendingOrders() {
		return orderService.getPendingOrders("");
	}

	/**
	 * Gets confirmed orders.
	 *
	 * @return the confirmed orders
	 */
	@GetMapping(path = "/confirmed")
	@ResponseBody
	public List<Orders> getConfirmedOrders() {
		return orderService.getConfirmedOrders("Deck");
	}



}
