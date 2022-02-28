package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents a report made when equipment is used
 *
 * it has a LatLng to show where it was used, as well as a date,
 * and user who used the item
 */
@Entity
@Table(name = "Reports")
public class Report {

  @Id
  @Column(name = "PK_ReportID")
  private Long id;

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

  @Transient
  private String fullName;

  public Report(String productName, int quantity, float latitude, float longitude, Date registrationDate, String fullName) {
    this.productName = productName;
    this.quantity = quantity;
    this.longitude = longitude;
    this.latitude = latitude;
    this.registrationDate = registrationDate;
    this.fullName = fullName;
  }

  /**
   * Gets the product id
   * @return the product id
   */
  public Long getId() {
    return id;
  }


  /**
   * Gets the product name
   * @return the product name
   */
  public String getProductName() {
    return productName;
  }

  /**
   * Gets the quantity of product used
   * @return the quantity of product used
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Gets the longitude the product was used at
   * @return the longitude the product was used at
   */
  public float getLongitude() {
    return longitude;
  }

  public String getLatLng() {
    return getLatitude() + ", " + getLongitude();
  }

  /**
   * Gets the latitude the product was used at
   * @return the latitude the product was used at
   */
  public float getLatitude() {
    return latitude;
  }

  /**
   * Gets the date the product was used
   * @return the date the product was used
   */
  public Date getRegistrationDate() {
    return registrationDate;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
