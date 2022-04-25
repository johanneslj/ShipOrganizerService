package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitializeDB implements CommandLineRunner {
  @Value("${user.properties.name}")
  private String name;
  @Value("${user.properties.username}")
  private String username;
  @Value("${user.properties.password}")
  private String password;
  private final UserService userService;

  public InitializeDB(UserService userService) {
    this.userService = userService;
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {
    if (userService.getByEmail(username).isEmpty()) {
      User user = new User(name, username, password);
      List<Department> departments = List.of(new Department("Skipper"));
      userService.registerAndGetSuccess(user, departments);
    }
  }
}
