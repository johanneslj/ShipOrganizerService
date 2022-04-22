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

	public Order() {
	}

	public Order(String imageName, String departmentName) {
		this.imageName = imageName;
		this.departmentName = departmentName;
	}

	public Order(int id, String imageName, String departmentName, int status) {
		this.id = id;
		this.imageName = imageName;
		this.departmentName = departmentName;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getStatus() {
		return this.status;
	}

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
