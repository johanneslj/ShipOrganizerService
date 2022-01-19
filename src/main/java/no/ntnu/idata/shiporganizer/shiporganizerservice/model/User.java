package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  // TODO Update departments to actual department.
  // Possible to store department columns as a list?
  @Column(name = "department_a")
  private int department_a;

  @Column(name = "department_b")
  private int department_b;

  @Column(name = "department_c")
  private int department_c;

  @Column(name = "department_d")
  private int department_d;

  @Column(name = "department_e")
  private int department_e;

  @Column(name = "department_f")
  private int department_f;

  @Column(name = "department_g")
  private int department_g;
}
