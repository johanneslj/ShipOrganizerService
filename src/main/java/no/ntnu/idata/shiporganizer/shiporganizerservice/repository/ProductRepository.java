package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for the products. This interface represents the connection to database
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	/**
	 * Gets all product
	 * @return List of all products
	 */
	List<Product> findAll();

	/**
	 * Gets all the products from the database with current stock
	 * @param dep Users selected department
	 * @return String list from the database
	 */
	@Query(value = "EXEC SelectAll @Calltime='Inventory' , @Department= :dep , @Username='';",nativeQuery = true)
	List<String> getProductInventory(@Param(value = "deck") String dep);
	/**
	 * Gets all the products from the database with the difference in stock and desired stock
	 * @param dep Users selected department
	 * @return String list from the database
	 */
	@Query(value = "EXEC SelectAll @Calltime='Preferred' , @Department= :dep , @Username='';",nativeQuery = true)
	List<String> getProductPreferredInventory(@Param(value = "deck") String dep);

}
