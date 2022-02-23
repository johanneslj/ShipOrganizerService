package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA Entity model for orders of the ship organizer service.
 *
 * @author Simon Duggal
 */
@Entity
@Table(name = "Orders")
public class Orders {
	@Id
	@Column(name = "PK_OrderID", nullable = false)
	private int id;

	@Column(name = "Imagename")
	private String imagename;

	@Column(name = "FK_DepartmentID")
	private int departmentID;

	@Column(name = "Status")
	private int status;

	private String DepartmentName;



	/**
	 * Instantiates a new Orders.
	 */
	public Orders() {
	}

	/**
	 * Instantiates a new Orders.
	 *
	 * @param imagename      the imagename
	 * @param departmentName the department name
	 */
	public Orders(String imagename, String departmentName) {
		this.imagename = imagename;
		this.DepartmentName = departmentName;
	}

	/**
	 * Gets imagename.
	 *
	 * @return the imagename
	 */
	public String getImagename() {
		return imagename;
	}

	/**
	 * Gets department id.
	 *
	 * @return the department id
	 */
	public int getDepartmentID() {
		return departmentID;
	}

	/**
	 * Gets status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Gets department name.
	 *
	 * @return the department name
	 */
	public String getDepartmentName() {
		return DepartmentName;
	}
}
