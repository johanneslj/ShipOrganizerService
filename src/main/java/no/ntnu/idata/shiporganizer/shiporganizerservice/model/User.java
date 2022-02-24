package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a user of the ship organizer service.
 * Implements UserDetails for use with Spring Security authentication.
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

  @Column(name = "Fullname")
  private String fullname;

  @Column(name = "Username", nullable = false)
  private String email;

  @Column(name = "Password")
  private String password;

  @Column(name = "Token")
  private String token;

  public User() {
  }

  public User(String fullname, String email, String password) {
    this.fullname = fullname;
    this.email = email;
    this.password = password;
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

  public User(String fullname, String email) {
    this.fullname = fullname;
    this.email = email;
  }

  /*--------------------------------
  Getters and setters for all fields:
  ----------------------------------*/

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
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", fullname='" + fullname + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", token='" + token + '\'' +
        '}';
  }
}
