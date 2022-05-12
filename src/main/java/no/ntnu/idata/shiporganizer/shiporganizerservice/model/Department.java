package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for the department entity.
 * Keeps track of department name and the rights value belonging to the department.
 */
@Entity
@Table(name = "Department")
public class Department {

  @Id
  @Column(name = "PK_DepartmentID")
  private int id;

  @Column(name = "DepartmentName")
  private String name;

  @Column(name = "Rights")
  private int rights;

  /**
   * Instantiates a Department.
   */
  public Department() {
  }

  /**
   * Instantiates a new Department.
   *
   * @param name the departments name
   */
  public Department(String name) {
    this.name = name;
  }

  /**
   * Instantiates a new Department.
   *
   * @param id     the departments id
   * @param name   the departments name
   * @param rights the departments rights, For admin 1 and 0 for normal users
   */
  public Department(int id, String name, int rights) {
    this.id = id;
    this.name = name;
    this.rights = rights;
  }

  /**
   * Gets departments id.
   *
   * @return the departments id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets departments new id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets departments name.
   *
   * @return the departments name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets departments name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets departments rights.
   *
   * @return the departments rights
   */
  public int getRights() {
    return rights;
  }

  /**
   * Sets departments rights.
   *
   * @param rights the new rights
   */
  public void setRights(int rights) {
    this.rights = rights;
  }

  @Override
  public String toString() {
    return "Department{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", rights=" + rights +
        '}';
  }
}
