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

  /**
   * Instantiates a new User.
   */
  public User() {
  }

  /**
   * Instantiates a  User.
   *
   * @param fullname the users fullname
   * @param email    the users email
   * @param password the users password
   */
  public User(String fullname, String email, String password) {
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  /**
   * Instantiates a  User.
   *
   * @param id       the users id
   * @param fullname the users fullname
   * @param email    the users email
   * @param password the users password
   */
  public User(int id, String fullname, String email, String password) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  /**
   * Instantiates a User.
   *
   * @param id       the users id
   * @param token    the users token
   * @param fullname the users fullname
   * @param email    the users email
   * @param password the  users password
   */
  public User(int id, String token, String fullname, String email, String password) {
    this.id = id;
    this.token = token;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  /**
   * Instantiates a User.
   *
   * @param fullname the users fullname
   * @param email    the users email
   */
  public User(String fullname, String email) {
    this.fullname = fullname;
    this.email = email;
  }

  /*--------------------------------
  Getters and setters for all fields:
  ----------------------------------*/

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets fullname.
   *
   * @return the fullname
   */
  public String getFullname() {
    return fullname;
  }

  /**
   * Sets fullname.
   *
   * @param fullname the fullname
   */
  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets token.
   *
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * Sets token.
   *
   * @param token the token
   */
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
