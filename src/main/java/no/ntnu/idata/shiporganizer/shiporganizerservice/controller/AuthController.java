package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import com.auth0.jwt.JWT;
import com.microsoft.sqlserver.jdbc.StringUtils;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.JWTProperties;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.LoginService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Endpoint for user login services.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
  final private LoginService loginService;
  final private UserService userService;

  // TODO Remove this field.
  final private JWTProperties jwtProps;

  public AuthController(LoginService loginService, UserService userService,
                        JWTProperties jwtProps) {
    this.loginService = loginService;
    this.userService = userService;

    this.jwtProps = jwtProps;
  }

  /**
   * Provides user information on correct login.
   *
   * @param http HttpEntity of request.
   * @return ResponseEntity containing user's token, or empty 404 status on incorrect login/missing user.
   */
  @PostMapping("/login")
  ResponseEntity<String> login(HttpEntity<String> http) {
    System.out.println(http.getHeaders());
    System.out.println(http.getBody());
    try {
      JSONObject json = new JSONObject(http.getBody());

      String email = json.getString("email");
      String password = json.getString("password");

      // TODO Remove prints.
      System.out.println(email);
      System.out.println(password);

      String token = loginService.login(email, password);

      // Returns found token if it exists.
      if (StringUtils.isEmpty(token)) {
        return ResponseEntity.notFound().build();
      } else {
        System.out.println(JWT.require(HMAC512(jwtProps.getSecretCode().getBytes()))
            .build()
            .verify(token)
            .getClaim("email").asString());
        return ResponseEntity.ok(token);
      }

    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
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
    // TODO Remove prints
    System.out.println(entity.getBody());
    try {
      JSONObject json = new JSONObject(entity.getBody());
      List<String> roles = new ArrayList<>();

      JSONArray jsonRoles = json.getJSONArray("roles");
      for (int i = 0; i < jsonRoles.length(); i++) {
        roles.add(jsonRoles.getString(i));
      }

      User user =
          new User(json.getString("fullname"), json.getString("email"), json.getString("password"),
              roles);

      System.out.println(user.getEmail() + user.getFullname() + user.getPassword());
      // TODO Implement with Spring Security for registration.

      // Get JSON array of departments and create list of departments.
      List<Department> departments = new ArrayList<Department>();
      JSONArray jsonArray = json.getJSONArray("departments");
      System.out.println(jsonArray);

      // Add departments to list.
      for (int i = 0; i < jsonArray.length(); i++) {
        departments.add(new Department(jsonArray.getString(i)));
      }

      // Add user with departments to database using service.
      userService.register(user, departments);

      // Registration successful.
      return ResponseEntity.ok("User successfully registered.");
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
