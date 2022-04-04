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

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public Department(int id, String name, int rights) {
    this.id = id;
    this.name = name;
    this.rights = rights;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRights() {
    return rights;
  }

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
