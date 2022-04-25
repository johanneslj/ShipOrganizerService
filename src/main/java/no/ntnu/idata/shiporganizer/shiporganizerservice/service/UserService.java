package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.DepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserDepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  final private UserRepository userRepository;
  final private DepartmentRepository departmentRepository;
  final private UserDepartmentRepository userDepartmentRepository;
  final private MailService mailService;
  final private PasswordEncoder passwordEncoder;
  final private LoginService loginService;

  public UserService(UserRepository userRepository,
                     DepartmentRepository departmentRepository,
                     UserDepartmentRepository userDepartmentRepository,
                     MailService mailService,
                     PasswordEncoder passwordEncoder,
                     LoginService loginService) {
    this.userRepository = userRepository;
    this.departmentRepository = departmentRepository;
    this.userDepartmentRepository = userDepartmentRepository;
    this.mailService = mailService;
    this.passwordEncoder = passwordEncoder;
    this.loginService = loginService;
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

  public Optional<User> getByEmail(String email) {
    return userRepository.findFirstByEmail(email);
  }

  public boolean emailExists(String email) {
    return userRepository.findFirstByEmail(email).isPresent();
  }

  /**
   * Registers a new user with access to specified departments.
   *
   * @param user        User to register.
   * @param departments List of departments the user gets access to.
   */
  public boolean registerAndGetSuccess(User user, List<Department> departments) {
    userRepository.addUser(user.getEmail(), "", user.getFullname());

    updateUserDepartments(user, departments);
    if(!user.getEmail().equals("admin@admin.com")){
      mailService.sendRegisteredEmail(user.getEmail());
    }


    return setNewTokenForUser(user.getEmail());
  }

  private void updateUserDepartments(User user, List<Department> departments) {
    userDepartmentRepository.updateUserDepartment(user.getEmail(),
        getDepartmentsString(departments));
  }

  private String getDepartmentsString(List<Department> departments) {
    StringBuilder departmentsString = new StringBuilder();
    departments.forEach(department -> {
      departmentsString.append(department.getName());
      departmentsString.append(",");
    });
    return departmentsString.toString();
  }

  public boolean sendNewPasswordEmail(String email) {
    Optional<User> userOptional = userRepository.findFirstByEmail(email);
    if (userOptional.isEmpty()) {
      return false;
    }
    User user = userOptional.get();
    setNewTokenForUser(user.getEmail());
    String verificationCode = user.getToken().substring(user.getToken().length() - 6);
    mailService.sendNewPasswordVerificationCode(user.getEmail(), verificationCode);

    return true;
  }

  public void editUser(User editedUser, String oldEmail, List<Department> departments) {
    userRepository.editUser(editedUser.getEmail(), editedUser.getFullname(), oldEmail);
    updateUserDepartments(editedUser, departments);
  }

  /**
   * Use the received verification code to set a new password for the user.
   *
   * @param email            Email of user.
   * @param verificationCode Received verification code for user.
   * @param password         New password to set for user.
   * @return True on success.
   */
  public boolean setNewPasswordWithVerificationCode(String email,
                                                    String verificationCode,
                                                    String password) {
    Optional<User> userOptional = userRepository.findFirstByEmail(email);
    userOptional.ifPresent(user -> {
      user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    });

    return checkValidVerificationCode(email, verificationCode) && userOptional.isPresent();
  }

  /**
   * Checks if the verification code is valid for given email address.
   *
   * @param email            User's email address.
   * @param verificationCode Verification code received by email.
   * @return True if valid.
   */
  public boolean checkValidVerificationCode(String email, String verificationCode) {
    Optional<User> userOptional = userRepository.findFirstByEmail(email);
    if (userOptional.isEmpty()) {
      return false;
    }
    User user = userOptional.get();
    // Check that verification code is correct. Verification code is last 6 characters in token.
    return user.getToken().substring(user.getToken().length() - 6).equals(verificationCode);
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
   * @param token Token of user to check admin rights of.
   * @return True if user is admin.
   */
  public boolean isUserAdmin(String token) {
    Optional<User> userOptional = getByToken(token);
    if (userOptional.isEmpty()) {
      return false;
    }
    User user = userOptional.get();
    List<Department> departments = getDepartments(user);

    return departments.stream().anyMatch(department -> department.getRights() == 1);
  }

  /**
   * Get all departments that user is a member of.
   *
   * @param user User to get departments of.
   * @return List if departments the user is a member of.
   */
  public List<Department> getDepartments(User user) {
    return userDepartmentRepository
        .getUserDepartmentsByUserID(user.getId())
        .stream()
        .map(userDepartment -> departmentRepository.findDepartmentById(
            userDepartment.getDepartmentID()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  /**
   * Sets a new token for the user.
   *
   * @param email Email of the user.
   * @return True on success.
   */
  private boolean setNewTokenForUser(String email) {
    Optional<User> userOptional = getByEmail(email);

    userOptional.ifPresent(user -> {
      user.setToken(loginService.buildJWT(user.getId(), user.getEmail(), user.getFullname()));
      userRepository.save(user);
    });

    return userOptional.isPresent();
  }
}
