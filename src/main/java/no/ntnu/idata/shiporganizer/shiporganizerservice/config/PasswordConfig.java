package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import java.security.SecureRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Returns password encoder with salting.
    return new BCryptPasswordEncoder(10, new SecureRandom());
  }
}
