package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.LoginService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for user login services.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
  final private LoginService loginService;
  final private UserService userService;

  /**
   * Creates an instance of the Auth controller
   * @param loginService
   * @param userService
   */
  public AuthController(LoginService loginService,
                        UserService userService) {
    this.loginService = loginService;
    this.userService = userService;
  }

  /**
   * Provides user information on correct login.
   *
   * @param entity HttpEntity of request.
   * @return ResponseEntity containing user's token, or empty 404 status on incorrect login/missing user.
   */
  @PostMapping("/login")
  ResponseEntity<User> login(HttpEntity<String> entity) {
    try {
      Optional<User> userOptional = getUserOptionalFromHttpEntity(entity);
      if (userOptional.isPresent()) {
        User user = userOptional.get();
        user.setPassword("");
        return ResponseEntity.ok(user);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }
  private Optional<User> getUserOptionalFromHttpEntity(HttpEntity<String> entity)
      throws JSONException {
    JSONObject json = new JSONObject(entity.getBody());
    return loginService.loginAndGetUserOptional(
        json.getString("email"),
        json.getString("password"));
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
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(HttpEntity<String> entity) {
    try {
      JSONObject json = new JSONObject(entity.getBody());
      String name = json.getString("fullname");
      String email = json.getString("email");
      if (userService.emailExists(email)) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
      User user = new User(name, email);
      if (!userService.registerAndGetSuccess(user, getDepartmentsFromJson(json))) {
        return ResponseEntity.unprocessableEntity().build();
      }
      return ResponseEntity.ok("User successfully registered.");
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  private List<Department> getDepartmentsFromJson(JSONObject json) throws JSONException {
    List<Department> departments = new ArrayList<>();
    JSONArray jsonArray = json.getJSONArray("departments");
    System.out.println(jsonArray);
    for (int i = 0; i < jsonArray.length(); i++) {
      departments.add(new Department(jsonArray.getString(i)));
    }
    return departments;
  }
}
