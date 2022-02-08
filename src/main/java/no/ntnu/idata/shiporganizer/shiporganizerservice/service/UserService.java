package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  final private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void addNewUserWithDepartments(User user, List<Department> departments) {
    userRepository.addUser(user.getEmail(), user.getPassword(), user.getFullname());

    StringBuilder departmentsString = new StringBuilder();
    for (Department department : departments) {
      departmentsString.append(department.getName());
      departmentsString.append(",");
    }

    userRepository.updateUserDepartment(user.getEmail(), departmentsString.toString());
  }

  public void deleteUser(String username) {
    userRepository.deleteUser(username);
  }

  /**
   * Get all departments user is a member of.
   *
   * @param user User to get departments of.
   * @return List if departments the user is a member of.
   */
  List<Department> getDepartments(User user) {
    List<Department> departments = new ArrayList<Department>();
    List<String> userDepartments = userRepository.getUserDepartments(user.getEmail());

    for (String userDepartment : userDepartments) {
      // TODO Handle string and add departments.

    }

    return departments;
  }
}
