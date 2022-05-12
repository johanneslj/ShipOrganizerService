package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserDepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Class to initialize a new database with the default super user
 */
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

  /**
   * Instantiates a InitializeDB.
   *
   * @param userRepository           the user repository
   * @param userDepartmentRepository the user department repository
   * @param passwordEncoder          the password encoder
   */
  public InitializeDB(UserRepository userRepository,
                      UserDepartmentRepository userDepartmentRepository,
                      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userDepartmentRepository = userDepartmentRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Callback used to run the bean.
   * Creates the user in the database with correct username, password and department
   * @param args incoming main method arguments
   */
  @Override
  public void run(String... args) {
    if (userRepository.findFirstByEmail(username).isEmpty()) {
      userRepository.addUser(username, passwordEncoder.encode(password), name);
      userDepartmentRepository.updateUserDepartment(username, "Skipper,");
    }
  }
}
