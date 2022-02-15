package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order service. The link between the Controller and the repository classes
 */
@Service
public class OrderService {

	/**
	 * The Order repository.
	 */
	@Autowired
	private final OrderRepository orderRepository;

	/**
	 * Instantiates a new Order service.
	 *
	 * @param orderRepository the order repository
	 */
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	/**
	 * Get pending orders list.
	 *
	 * @param department the department
	 * @return the list
	 */
	public List<Orders> getPendingOrders(String department){
		List<Orders> pendingOrders = new ArrayList<>();
		List<String> bits = orderRepository.getPendingOrders(department);
		for (String ting : bits) {
			String[] data = ting.split(",");
			pendingOrders.add(new Orders(data[0],data[1]));
		}
		return pendingOrders;
	}

	/**
	 * Get confirmed orders list.
	 *
	 * @param department the department
	 * @return the list
	 */
	public List<Orders> getConfirmedOrders(String department){
		List<Orders> confirmedOrders = new ArrayList<>();
		List<String> bits = orderRepository.getConfirmedOrders(department);
		for (String ting : bits) {
			String[] data = ting.split(",");
			confirmedOrders.add(new Orders(data[0],data[1]));
		}
		return confirmedOrders;
	}
	/**
	 * Insert new order for confirmation
	 *
	 * @param requestBody the request body containing users department and image-name
	 * @return Success or empty depending on if the query completed or not
	 */
	public String insertNewOrder(String requestBody){
		String[] bits = requestBody.split(",");
		String department = bits[0];
		String imagename = bits[1];
		//TODO Send image to image server
		int result = orderRepository.insertNewOrder(department,imagename);
		if(result == 1){
			return "Success";
		}
		return "";
	}

	/**
	 * Updates order from pending to confirmed
	 *
	 * @param requestBody the request body containing users department and image-name
	 * @return Success or empty depending on if the query completed or not
	 */
	public String updateOrder(String requestBody){
		String[] bits = requestBody.split(",");
		String department = bits[0];
		String imagename = bits[1];
		//TODO Send image to image server
		int result = orderRepository.updateOrder(department,imagename);
		if(result == 1){
			return "Success";
		}
		return "";
	}


}
