package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The interface Order repository. Interface used for the connection to the database.
 */
public interface OrderRepository extends JpaRepository<Orders,Integer> {

	/**
	 * Gets pending orders.
	 *
	 * @param dep the user selected department
	 * @return List of pending orders
	 */
	@Query(value = "SELECT * from getorders('Pending', :dep);",nativeQuery = true)
	List<String> getPendingOrders(@Param(value = "dep") String dep);

	/**
	 * Gets confirmed orders.
	 *
	 * @param dep the user selected department
	 * @return List of confirmed orders
	 */
	@Query(value = "SELECT * from getorders('Confirmed', :dep);",nativeQuery = true)
	List<String> getConfirmedOrders(@Param(value = "dep") String dep);

	/**
	 * Inserts new order for confirmation
	 *
	 * @param dep the user selected department
	 * @param imageName The image name for the bill
	 * @return int 1 if the query is completed
	 */
	@Query(value = "EXEC HandleOrders @Calltime='New' , @Department= :dep , @Imagename=:imageName ;",nativeQuery = true)
	@Modifying
	int insertNewOrder(@Param(value = "dep") String dep , @Param(value = "imageName") String imageName);

	/**
	 * Updates order from pending to confirmed
	 *
	 * @param dep the user selected department
	 * @param imagename The image name for the bill
	 * @return int 1 if the query is completed
	 */
	@Query(value = "EXEC HandleOrders @Calltime='Update' , @Department= :dep , @Imagename=:imagename ;",nativeQuery = true)
	@Modifying
	int updateOrder(@Param(value = "dep") String dep , @Param(value = "imagename") String imagename);



}
