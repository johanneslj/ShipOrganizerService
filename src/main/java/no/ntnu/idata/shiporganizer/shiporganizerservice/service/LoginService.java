package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import com.microsoft.sqlserver.jdbc.StringUtils;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class LoginService {
  final UserRepository userRepository;

  public LoginService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String login(String email, String password) {
    // TODO More security features here?
    Optional<User> foundUser = userRepository.findFirstUserByEmailAndPassword(email, password);

    if (foundUser.isPresent()) {
      // TODO Generate token in a better way to include date and check its unique.
      String token = UUID.randomUUID().toString();
      User user = foundUser.get();
      user.setToken(token);
      userRepository.save(user);
      return token;
    }

    return StringUtils.EMPTY;
  }

  public void register(User user, List<Department> departments) {
    userRepository.addUser(user.getEmail(), user.getPassword(), user.getFullname());

    StringBuilder departmentsString = new StringBuilder();
    for (Department department : departments) {
      departmentsString.append(department.getName());
      departmentsString.append(",");
    }

    userRepository.updateUserDepartment(user.getEmail(), departmentsString.toString());
  }

  public Optional<User> findByToken(String token) {
    return userRepository.findFirstByEmail(token); // TODO Change to token.
  }

}
