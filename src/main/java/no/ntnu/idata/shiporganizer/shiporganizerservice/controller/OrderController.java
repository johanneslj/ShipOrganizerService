package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
