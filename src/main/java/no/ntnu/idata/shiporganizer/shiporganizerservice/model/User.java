package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPA Entity model for user of the ship organizer service.
 *
 * @author johanneslj
 * @version 0.1
 */
@Entity
@Table(name = "LoginTable")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PK_UserID")
  private Long id;

  @Column(name = "Fullname")
  private String fullname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ship_id", referencedColumnName = "id")
  private Ship ship;

  // TODO Update departments to actual department.
  // Possible to store department columns as a list?
  @OneToMany(cascade = CascadeType.ALL)
  @Column(name = "departments")
  List<Department> departments;
}
