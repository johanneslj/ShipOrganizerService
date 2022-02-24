package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents the relationship between a user and a department.
 */
@Entity
@Table(name = "UserDepartment")
public class UserDepartment {

  @Id
  @Column(name = "PK_UserDepID")
  private int id;

  @Column(name = "FK_Department")
  private int departmentID;

  @Column(name = "FK_User")
  private int userID;

  @Transient
  private User user;

  @Transient
  private Department department;

  public UserDepartment() {
  }

  public UserDepartment(int id, int departmentID, int userID) {
    this.id = id;
    this.departmentID = departmentID;
    this.userID = userID;
  }

  public UserDepartment(int id, int departmentID, int userID,
                        User user,
                        Department department) {
    this.id = id;
    this.departmentID = departmentID;
    this.userID = userID;
    this.user = user;
    this.department = department;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDepartmentID() {
    return departmentID;
  }

  public void setDepartmentID(int departmentID) {
    this.departmentID = departmentID;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
