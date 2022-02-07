package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user of the ship organizer service.
 *
 * @author johanneslj
 * @version 0.2
 */
@Entity
@Table(name = "LoginTable")
public class User implements UserDetails {

  @Id
  @Column(name = "PK_UserID")
  private int id;

  @Column(name = "Fullname")
  private String fullname;

  @Column(name = "Username", nullable = false)
  private String email;

  @Column(name = "Password", nullable = false)
  private String password;

  // TODO Should be stored in database
  @Transient
  private String token;

  /*
   * Used for UserDetails.
   */

  @Transient
  private Boolean enabled;

  @Transient
  private Boolean accountNonExpired;

  @Transient
  private Boolean accountNonLocked;

  @Transient
  private boolean credentialsNonExpired;

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

  public User(int id, String fullname, String email, String password, String token,
              Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked,
              boolean credentialsNonExpired) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
    this.token = token;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  /*
   * UserDetails implementation for use with Spring Security
   */

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
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
