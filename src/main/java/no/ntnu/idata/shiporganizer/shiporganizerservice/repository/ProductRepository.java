package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for the products. This interface represents the connection to database
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Gets all product
     *
     * @return List of all products
     */
    List<Product> findAll();

    /**
     * Gets all the products from the database with current stock
     *
     * @param dep Users selected department
     * @return String list from the database
     */
    @Query(value = "Call HandleProduct('InitialInventory',:dep,'','','',0,0,'');", nativeQuery = true)
    List<Product> getInitialProductInventory(@Param(value = "dep") String dep);

    /**
     * Gets all the products from the database with current stock
     *
     * @param dep  Users selected department
     * @param date last time user fetched the inventory
     * @return String list from the database
     */
    @Query(value = "Call HandleProduct('UpdatedInventory',:dep,'','','',0,0,:date);;", nativeQuery = true)
    List<Product> getUpdatedProductInventory(@Param(value = "dep") String dep, @Param(value = "date") String date);


    /**
     * Creates a new product in the database
     *
     * @param name          the name of the product
     * @param productNumber the product number
     * @param desiredStock  the desired amount of the item
     * @param stock         the current amount of the item
     * @param barcode       the barcode of the item
     * @param dep           the department to which the product will be registered
     * @return 1 if success else 0
     */
    @Query(value = "Call HandleProduct('Insert',:dep,:name,:productNumber,:barcode,:desiredStock,:stock,:dateTime);", nativeQuery = true)
    @Modifying
    int createNewProduct(@Param(value = "name") String name, @Param(value = "productNumber") String productNumber, @Param(value = "desiredStock") int desiredStock, @Param(value = "stock") int stock, @Param(value = "barcode") String barcode, @Param(value = "dep") String dep, @Param(value = "dateTime") String dateTime);

    /**
     * Deletes a product in the database
     *
     * @param productNumber the product number
     * @return 1 if success else 0
     */
    @Query(value = "Call HandleProduct('Delete','','',:productNumber,'',0,0,'');", nativeQuery = true)
    @Modifying
    int deleteProduct(@Param(value = "productNumber") String productNumber);




    /**
     * Edits an existing product
     *
     * @param name          the new name for the product
     * @param productNumber the product number used to find the product to edit
     * @param desiredStock  the new desired stock
     * @param barcode       the new barcode
     * @param dep           the department to which the product is registered
     * @return 1 if success else 0
     */
    @Query(value = "Call HandleProduct('Update',:dep,:name,:productNumber,:barcode,:desiredStock,:stock,:dateTime);", nativeQuery = true)
    @Modifying
    int editProduct(@Param(value = "name") String name, @Param(value = "productNumber") String productNumber, @Param(value = "desiredStock") int desiredStock, @Param(value = "barcode") String barcode, @Param(value = "dep") String dep, @Param(value = "dateTime") String dateTime);


    /**
     * Gets all the products from the database with the difference in stock and desired stock
     *
     * @param dep Users selected department
     * @return String list from the database
     */
    @Query(value = "Call HandleProduct('Recommended',:dep,'','','',0,0,'');", nativeQuery = true)
    List<Product> getProductRecommendedInventory(@Param(value = "dep") String dep);


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
    @Query(value = "Call InsertRecordAndUpdateStorelink(:productno,:username,:quantity,:Longitude,:Latitude,:date) ;", nativeQuery = true)
    @Modifying
    int setNewStock(@Param(value = "productno") String productno, @Param(value = "username") String username, @Param(value = "quantity") int quantity, @Param(value = "Longitude") float Longitude, @Param(value = "Latitude") float Latitude, @Param(value = "date") String date);


}

