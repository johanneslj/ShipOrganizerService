package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class used to test if the server is running
 */
@RestController
@RequestMapping("/connection")
public class ConnectionController {
  /**
   * Get method to check if server is running by repositioning 200 if running
   */
  @GetMapping
  public void connect() {
  }
}
