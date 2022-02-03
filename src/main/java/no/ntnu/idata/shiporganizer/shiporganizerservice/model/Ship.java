package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Model class for the ship entity within the ship organizer service.
 */
@Entity
@Table(name = "ship")
public class Ship {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;
}
