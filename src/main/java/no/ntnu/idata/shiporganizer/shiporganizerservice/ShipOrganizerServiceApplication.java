package no.ntnu.idata.shiporganizer.shiporganizerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class ShipOrganizerServiceApplication {

  @Autowired
  private JavaMailSender javaMailSender;


  public static void main(String[] args) {
    SpringApplication.run(ShipOrganizerServiceApplication.class, args);
  }

}
