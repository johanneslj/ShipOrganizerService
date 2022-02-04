package no.ntnu.idata.shiporganizer.shiporganizerservice.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA Entity model for product of the ship organizer service.
 *
 * @author Simon Duggal
 */
@Entity
@Table(name = "Product")
public class Product {
	@Id
	@Column(name = "PK_ProdID", nullable = false)
	private int id;

	@Column(name = "ProductNumber")
	private String productnumber;

	@Column(name = "ProductName")
	private String productname;

	@Column(name = "EAN")
	private String barcode;

	/**
	 * Gets product number.
	 *
	 * @return the product number
	 */
	public String getProductNumber() {
		return productnumber;
	}

	/**
	 * Gets product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productname;
	}

	/**
	 * Gets barcode.
	 *
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
}
