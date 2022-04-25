package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserDepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitializeDB implements CommandLineRunner {
  @Value("${user.properties.name}")
  private String name;
  @Value("${user.properties.username}")
  private String username;
  @Value("${user.properties.password}")
  private String password;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserDepartmentRepository userDepartmentRepository;

  public InitializeDB(UserRepository userRepository,
                      UserDepartmentRepository userDepartmentRepository,
                      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userDepartmentRepository = userDepartmentRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {
    if (userRepository.findFirstByEmail(username).isEmpty()) {
      userRepository.addUser(username, passwordEncoder.encode(password), name);
      userDepartmentRepository.updateUserDepartment(username, "Skipper,");
    }
  }
}
