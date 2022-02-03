package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reports")
public class Report {

  @Id
  @Column(name = "PK_ReportID")
  private Long id;
  
  @Column(name = "FK_Product")
  private int product;

  @Column(name = "FK_UserID")
  private int userId;

  @Column(name = "ProductName")
  private String productName;

  @Column(name = "Quantity")
  private int quantity;

  @Column(name = "Longitude")
  private float longitude;

  @Column(name = "Latitude")
  private float latitude;

  @Column(name = "Regdate")
  private Date registrationDate;

  /**
   * Gets the product id
   * @return the product id
   */
  private Long getId() {
    return id;
  }

  /**
   * gets the product key
   * @return the product key
   */
  private int getProduct() {
    return product;
  }

  /**
   * Gets the userid of the user who registered the product usage
   * @return the userid of the user who registered the product usage
   */
  private int getUserId() {
    return userId;
  }

  /**
   * Gets the product name
   * @return the product name
   */
  private String getProductName() {
    return productName;
  }

  /**
   * Gets the quantity of product used
   * @return the quantity of product used
   */
  private int getQuantity() {
    return quantity;
  }

  /**
   * Gets the longitude the product was used at
   * @return the longitude the product was used at
   */
  private float getLongitude() {
    return longitude;
  }

  /**
   * Gets the latitude the product was used at
   * @return the latitude the product was used at
   */
  private float getLatitude() {
    return latitude;
  }

  /**
   * Gets the date the product was used
   * @return the date the product was used
   */
  private Date getRegistrationDate() {
    return registrationDate;
  }
}
