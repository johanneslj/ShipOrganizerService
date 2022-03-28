package no.ntnu.idata.shiporganizer.shiporganizerservice.model;


import javax.persistence.*;

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

	private String stock;

	private String desiredStock;

	public Product(String productname,String productnumber, String barcode, String stock) {
		this.productnumber = productnumber;
		this.productname = productname;
		this.barcode = barcode;
		this.stock = stock;
	}

	public Product() {

	}

	/**
	 * Gets stock.
	 *
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}

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
