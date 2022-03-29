package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
  @GetMapping
  public void connect() {
  }
}
