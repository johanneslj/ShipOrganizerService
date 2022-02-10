package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Orders,Integer> {

	/**
	 * Gets pending orders.
	 *
	 * @param dep the user selected department
	 * @return List of pending orders
	 */
	@Query(value = "EXEC HandleOrders @Calltime='Pending' , @Department= :dep , @Imagename='';",nativeQuery = true)
	List<String> getPendingOrders(@Param(value = "dep") String dep);
}
