package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;

public class UserService {
  final private UserRepository userRepository;

  public UserService(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  List<Department> getDepartments(User user) {
    List<Department> departments = new ArrayList<Department>();

    for (String userDepartment : userRepository.getUserDepartments(user.getEmail())) {
      // TODO Handle string and add departments.
    }

    return departments;
  }
}
