package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findAll();

	@Query(value = "EXEC SelectAll @Calltime='Inventory' , @Department= :deck , @Username='';",nativeQuery = true)
	List<String> getProductInventory(@Param(value = "deck") String deck);

	@Query(value = "EXEC SelectAll @Calltime='Preferred' , @Department= :deck , @Username='';",nativeQuery = true)
	List<String> getProductPreferredInventory(@Param(value = "deck") String deck);

}
