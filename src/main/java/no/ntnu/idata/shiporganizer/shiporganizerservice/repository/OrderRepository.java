package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * The interface Order repository. Interface used for the connection to the database.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	/**
	 * Gets an order based on id
	 * @param id the id of the order to be found
	 * @return Order with corresponding id if it exists
	 */
	Optional<Order> findById(int id);
	/**
	 * Gets an order based on image name
	 * @param imageName the image name of the order to be found
	 * @return Order with corresponding image name if it exists
	 */
	Optional<Order> findOrderByImageName(String imageName);

	/**
	 * Gets pending orders.
	 *
	 * @param dep the user selected department
	 * @return List of pending orders
	 */
	@Query(value = "Call HandleOrders('Pending',:dep,'',0);",nativeQuery = true)
	List<String> getPendingOrders(@Param(value = "dep") String dep);

	/**
	 * Gets confirmed orders.
	 *
	 * @param dep the user selected department
	 * @return List of confirmed orders
	 */
	@Query(value = "Call HandleOrders('Confirmed',:dep,'',0);",nativeQuery = true)
	List<String> getConfirmedOrders(@Param(value = "dep") String dep);

	/**
	 * Inserts new order for confirmation
	 *
	 * @param dep the user selected department
	 * @param imageName The image name for the bill
	 * @return int 1 if the query is completed
	 */
	@Query(value = "Call HandleOrders('New',:dep,:imageName,0);",nativeQuery = true)
	@Modifying
	int insertNewOrder(@Param(value = "dep") String dep , @Param(value = "imageName") String imageName);

	/**
	 * Updates order from pending to confirm
	 *
	 * @param dep the user selected department
	 * @param imageName The image name for the bill
	 * @return int 1 if the query is completed
	 */
	@Query(value = "Call HandleOrders('Update',:dep,:imageName,:status);",nativeQuery = true)
	@Modifying
	int updateOrder(@Param(value = "dep") String dep , @Param(value = "imageName") String imageName,@Param(value = "status") int status);
}
