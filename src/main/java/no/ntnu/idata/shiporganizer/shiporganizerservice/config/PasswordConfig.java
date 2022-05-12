package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import java.security.SecureRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class to encode the users' password using BCryptPasswordEncoder
 */
@Configuration
public class PasswordConfig {

  /**
   * Encodes the users' password with strength 10
   * @return An encoded password
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    // Returns password encoder with salting.
    return new BCryptPasswordEncoder(10, new SecureRandom());
  }
}
