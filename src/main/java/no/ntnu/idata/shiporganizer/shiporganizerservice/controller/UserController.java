package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user actions.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets a list of all users.
   */
  @GetMapping("/all-users")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Registers a new user with set departments.
   * <p>
   * Request body needs to be JSONObject in this format:
   * {
   * "fullname": "String with name",
   * "email": "user@email.com",
   * "password": "password",
   * "departments": ["Department1", "Department2", ...]
   * }
   *
   * @param entity HttpEntity of request
   * @return ResponseEntity
   */
  @PostMapping("/register-user")
  public ResponseEntity<String> registerUser(HttpEntity<String> entity) {
    try {
      JSONObject json = new JSONObject(entity.getBody());
      User user =
          new User(json.getString("fullname"), json.getString("email"), json.getString("password"));

      // TODO Implement with Spring Security for registration.

      // Get JSON array of departments and create list of departments.
      List<Department> departments = new ArrayList<Department>();
      JSONArray jsonArray = new JSONArray(json.getJSONArray("departments"));

      // Add departments to list.
      for (int i = 0; i < jsonArray.length(); i++) {
        departments.add(new Department(jsonArray.getString(i)));
      }

      // Add user with departments to database using service.
      userService.addNewUserWithDepartments(user, departments);

      // Registration successful.
      return ResponseEntity.ok("User successfully registered.");
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("delete-user")
  public ResponseEntity<String> deleteUser(HttpEntity<String> entity) {
    try {
      JSONObject json = new JSONObject(entity.getBody());

      // TODO Check if user is authorized to delete user.
      boolean isAuthorized = true;


      if (isAuthorized) {
        userService.deleteUser(json.getString("username"));

        return ResponseEntity.ok("User deleted.");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
