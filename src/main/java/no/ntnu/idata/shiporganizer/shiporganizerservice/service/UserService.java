package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.UserDepartment;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.DepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserDepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  final private UserRepository userRepository;
  final private DepartmentRepository departmentRepository;
  final private UserDepartmentRepository userDepartmentRepository;

  public UserService(UserRepository userRepository, DepartmentRepository departmentRepository,
                     UserDepartmentRepository userDepartmentRepository) {
    this.userRepository = userRepository;
    this.departmentRepository = departmentRepository;
    this.userDepartmentRepository = userDepartmentRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Finds a user by their token.
   *
   * @param token Token of user to find.
   * @return Optional of user. Is present if found.
   */
  public Optional<User> getByToken(String token) {
    return userRepository.findFirstUserByToken(token);
  }

  public boolean doesEmailExist(String email) {
    return userRepository.findFirstByEmail(email).isPresent();
  }

  /**
   * Registers a new user with access to specified departments.
   *
   * @param user        User to register.
   * @param departments List of departments the user gets access to.
   */
  public void register(User user, List<Department> departments) {
    userRepository.addUser(user.getEmail(), user.getPassword(), user.getFullname());

    StringBuilder departmentsString = new StringBuilder();
    for (Department department : departments) {
      departmentsString.append(department.getName());
      departmentsString.append(",");
    }

    userRepository.updateUserDepartment(user.getEmail(), departmentsString.toString());
  }

  /**
   * Delete authorized user on request.
   *
   * @param email Email of user to delete
   */
  public void deleteUser(String email) {
    userRepository.deleteUser(email);
  }

  /**
   * Checks if the user is admin.
   * <p>
   * A user is an admin if they have access to a department with admin rights (1).
   *
   * @param user User to check admin rights of.
   * @return True if user is admin.
   */
  public boolean isUserAdmin(User user) {
    List<Department> departments = getDepartments(user);

    for (Department department : departments) {
      if (department.getRights() == 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get all departments user is a member of.
   *
   * @param user User to get departments of.
   * @return List if departments the user is a member of.
   */
  public List<Department> getDepartments(User user) {
    List<Department> departments = new ArrayList<>();
    List<UserDepartment> userDepartments =
        userDepartmentRepository.getUserDepartmentsByUserID(user.getId());

    for (UserDepartment userDepartment : userDepartments) {
      Optional<Department> department =
          departmentRepository.findDepartmentById(userDepartment.getDepartmentID());
      department.ifPresent(departments::add);
      System.out.println(department);
    }
    return departments;
  }
}
