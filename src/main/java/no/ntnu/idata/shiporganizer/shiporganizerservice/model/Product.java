package no.ntnu.idata.shiporganizer.shiporganizerservice.model;


import java.util.Objects;
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

	private String Desired_Stock;

	/**
	 * Instantiates a Product.
	 *
	 * @param id            the products id
	 * @param productname   the products productname
	 * @param productnumber the products productnumber
	 * @param barcode       the products barcode
	 * @param stock         the products stock
	 * @param desiredStock  the products desired stock
	 */
	public Product(int id,String productname,String productnumber, String barcode, String stock, String desiredStock) {
		this.id = id;
		this.productnumber = productnumber;
		this.productname = productname;
		this.barcode = barcode;
		this.stock = stock;
		this.Desired_Stock  = desiredStock;
	}

	/**
	 * Instantiates a Product.
	 */
	public Product() {

	}

	/**
	 * Gets the products' id.
	 *
	 * @return the products' id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the products' desired stock.
	 *
	 * @return the products' desired stock
	 */
	public String getDesired_Stock() {
		return Desired_Stock;
	}

	/**
	 * Gets the products' stock.
	 *
	 * @return the products' stock
	 */
	public String getStock() {
		return stock;
	}

	/**
	 * Gets the product number.
	 *
	 * @return the product number
	 */
	public String getProductNumber() {
		return productnumber;
	}


	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productname;
	}

	/**
	 * Gets the products' barcode.
	 *
	 * @return the products' barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", productnumber='" + productnumber + '\'' +
				", productname='" + productname + '\'' +
				", barcode='" + barcode + '\'' +
				", stock='" + stock + '\'' +
				", Desired_Stock='" + Desired_Stock + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		return id == product.id && Objects.equals(productnumber, product.productnumber) &&
				Objects.equals(productname, product.productname) &&
				Objects.equals(barcode, product.barcode) &&
				Objects.equals(stock, product.stock) &&
				Objects.equals(Desired_Stock, product.Desired_Stock);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, productnumber, productname, barcode, stock, Desired_Stock);
	}
}
