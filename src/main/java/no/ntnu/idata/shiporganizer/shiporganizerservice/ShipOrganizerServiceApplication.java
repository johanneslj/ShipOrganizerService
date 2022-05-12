package no.ntnu.idata.shiporganizer.shiporganizerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application class
 * Responsible to start the application
 */
@SpringBootApplication
public class ShipOrganizerServiceApplication {
  /**
   * Main method to run the server
   * @param args Command line argument
   */
  public static void main(String[] args) {
    SpringApplication.run(ShipOrganizerServiceApplication.class, args);
  }
}
