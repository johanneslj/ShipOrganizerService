package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	List<String> getProductInventory(@Param(value = "dep") String dep);
	/**
	 * Gets all the products from the database with the difference in stock and desired stock
	 * @param dep Users selected department
	 * @return String list from the database
	 */
	@Query(value = "EXEC SelectAll @Calltime='Preferred' , @Department= :dep , @Username='';",nativeQuery = true)
	List<String> getProductPreferredInventory(@Param(value = "dep") String dep);


	/**
	 * Sets new stock.
	 *
	 * @param productno the product number for the specific product
	 * @param username  the users username
	 * @param quantity  the taken quantity
	 * @param Longitude the longitude when the user takes a product
	 * @param Latitude  the latitude when the user takes a product
	 * @return int 1 is success
	 */
	@Query(value = "EXEC InsertRecordAndUpdateStorelink @productno=:productno,@username=:username,@quantity =:quantity,@Longitude=:Longitude, @Latitude=:Latitude",nativeQuery = true)
	@Modifying
	int setNewStock(@Param(value = "productno") String productno, @Param(value = "username") String username, @Param(value = "quantity") int quantity, @Param(value = "Longitude")  float Longitude, @Param(value = "Latitude") float Latitude);

}

