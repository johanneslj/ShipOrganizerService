package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.service.OrderService;

/**
 * The type Order controller. Handles all the direct api calls and sends the variables to the service
 * for the correct handling
 */
public class OrderController {

	/**
	 * The Order service.
	 */
	OrderService orderService;

	/**
	 * Instantiates a new Order controller.
	 *
	 * @param orderService the order service
	 */
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

}
