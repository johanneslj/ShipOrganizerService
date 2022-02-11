package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Order service. The link between the Controller and the repository classes
 */
public class OrderService {

	/**
	 * The Order repository.
	 */
	@Autowired
	OrderRepository orderRepository;

	/**
	 * Instantiates a new Order service.
	 *
	 * @param orderRepository the order repository
	 */
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

}
