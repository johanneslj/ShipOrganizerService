package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import java.io.IOException;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Order;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Order service. The link between the Controller and the repository classes
 */
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderImageStorageService orderImageStorageService;

	/**
	 * Instantiates a new Order service.
	 *
	 * @param orderRepository          the order repository
	 * @param orderImageStorageService image storage service
	 */
	public OrderService(OrderRepository orderRepository,
						OrderImageStorageService orderImageStorageService) {
		this.orderRepository = orderRepository;
		this.orderImageStorageService = orderImageStorageService;
	}

	/**
	 * Get pending orders list.
	 *
	 * @param department the department
	 * @return the list
	 */
	public List<Order> getPendingOrders(String department) {
		List<Order> pendingOrders = new ArrayList<>();
		List<String> bits = orderRepository.getPendingOrders(department);
		for (String ting : bits) {
			String[] data = ting.split(",");
			pendingOrders.add(new Order(data[0], data[1],Integer.parseInt(data[2])));
		}
		return pendingOrders;
	}

	/**
	 * Get confirmed orders list.
	 *
	 * @param department the department
	 * @return the list
	 */
	public List<Order> getConfirmedOrders(String department) {
		List<Order> confirmedOrders = new ArrayList<>();
		List<String> bits = orderRepository.getConfirmedOrders(department);
		for (String ting : bits) {
			String[] data = ting.split(",");
			confirmedOrders.add(new Order(data[0], data[1],Integer.parseInt(data[2])));
		}
		return confirmedOrders;
	}

	public void insertNewOrder(String department, MultipartFile image) throws IOException {
		String imageName = OrderImageStorageService.getFileName(image);
		orderRepository.insertNewOrder(department, imageName);
		int orderId =
				orderRepository
						.findOrderByImageName(imageName)
						.orElse(new Order(-1, "", "", -1))
						.getId();
		orderImageStorageService.storeFile(image, orderId);
	}

	/**
	 * Updates order from pending to confirm
	 *
	 * @param department the department of the user.
	 * @param imageName  File name of the selected image.
	 * @param status  status 1 if order is confirmed and 0 if the order is rejected
	 * @return Success or empty depending on if the query completed or not
	 */
	public String updateOrder(String department, String imageName, int status) {
		int result = orderRepository.updateOrder(department, imageName, status);
		if (result == 1) {
			return "Success";
		}
		return "";
	}

	public void deleteAllOrders() {
		this.orderRepository.deleteAll();
	}
}
