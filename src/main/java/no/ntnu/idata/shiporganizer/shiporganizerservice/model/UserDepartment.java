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

  /**
   * Instantiates a User department.
   */
  public UserDepartment() {
  }

  /**
   * Instantiates a User department.
   *
   * @param id           the id
   * @param departmentID the department id
   * @param userID       the user id
   */
  public UserDepartment(int id, int departmentID, int userID) {
    this.id = id;
    this.departmentID = departmentID;
    this.userID = userID;
  }

  /**
   * Instantiates a User department.
   *
   * @param id           the id
   * @param departmentID the department id
   * @param userID       the user id
   * @param user         the user
   * @param department   the department
   */
  public UserDepartment(int id, int departmentID, int userID,
                        User user,
                        Department department) {
    this.id = id;
    this.departmentID = departmentID;
    this.userID = userID;
    this.user = user;
    this.department = department;
  }

  /**
   * Gets UserDepartment id.
   *
   * @return the UserDepartment id
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
   * Gets department id.
   *
   * @return the department id
   */
  public int getDepartmentID() {
    return departmentID;
  }

  /**
   * Sets department id.
   *
   * @param departmentID the department id
   */
  public void setDepartmentID(int departmentID) {
    this.departmentID = departmentID;
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public int getUserID() {
    return userID;
  }

  /**
   * Sets user id.
   *
   * @param userID the user id
   */
  public void setUserID(int userID) {
    this.userID = userID;
  }

  /**
   * Gets user.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets user.
   *
   * @param user the user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets department.
   *
   * @return the department
   */
  public Department getDepartment() {
    return department;
  }

  /**
   * Sets department.
   *
   * @param department the department
   */
  public void setDepartment(Department department) {
    this.department = department;
  }
}
