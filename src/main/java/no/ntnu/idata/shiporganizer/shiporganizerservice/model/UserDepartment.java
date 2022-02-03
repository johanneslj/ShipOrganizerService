package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
