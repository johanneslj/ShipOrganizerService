package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a user of the ship organizer service.
 *
 * @author johanneslj
 * @version 0.2
 */
@Entity
@Table(name = "LoginTable")
public class User {

  @Id
  @Column(name = "PK_UserID")
  private int id;
  
  private String token;

  @Column(name = "Fullname")
  private String fullname;

  @Column(name = "Username", nullable = false)
  private String email;

  @Column(name = "Password", nullable = false)
  private String password;

  public User() {
  }

  public User(int id, String fullname, String email, String password) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  public User(int id, String token, String fullname, String email, String password) {
    this.id = id;
    this.token = token;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  // TODO Update departments to actual department.
  // Possible to store department columns as a list?


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
