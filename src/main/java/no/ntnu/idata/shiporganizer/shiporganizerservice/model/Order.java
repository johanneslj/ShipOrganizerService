package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * JPA Entity model for orders of the ship organizer service.
 *
 * @author Simon Duggal
 */
@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@Column(name = "PK_OrderID", nullable = false)
	private int id;
	@Column(name = "Imagename")
	private String imageName;
	@Column(name = "FK_DepartmentID")
	private int departmentId;
	@Transient
	private String departmentName;
	@Column(name = "Status")
	private int status;

	/**
	 * Instantiates a Order.
	 */
	public Order() {
	}

	/**
	 * Instantiates a Order.
	 *
	 * @param imageName      the image name
	 * @param departmentName the department name
	 * @param status         the status
	 */
	public Order(String imageName, String departmentName,int status) {
		this.imageName = imageName;
		this.departmentName = departmentName;
		this.status = status;
	}

	/**
	 * Instantiates a Order.
	 *
	 * @param id             the id
	 * @param imageName      the image name
	 * @param departmentName the department name
	 * @param status         the status
	 */
	public Order(int id, String imageName, String departmentName, int status) {
		this.id = id;
		this.imageName = imageName;
		this.departmentName = departmentName;
		this.status = status;
	}

	/**
	 * Gets orders id.
	 *
	 * @return the orders id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets a new id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets image name.
	 *
	 * @return the image name
	 */
	public String getImageName() {
		return this.imageName;
	}

	/**
	 * Sets image name.
	 *
	 * @param imageName the image name
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * Gets department name.
	 *
	 * @return the department name
	 */
	public String getDepartmentName() {
		return this.departmentName;
	}

	/**
	 * Sets department name.
	 *
	 * @param departmentName the department name
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Gets the order status.
	 *
	 * @return the orders status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Sets a new status to the order.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", imageName='" + imageName + '\'' +
				", departmentId=" + departmentId +
				", departmentName='" + departmentName + '\'' +
				", status=" + status +
				'}';
	}
}
