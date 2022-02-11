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
import org.springframework.http.HttpHeaders;
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
@RequestMapping(value = "/api/user")
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
   * Lets a user delete themself, or an admin delete any user.
   *
   * @param entity HTTP Entity.
   * @return Response Entity.
   */
  @DeleteMapping("/delete-user")
  public ResponseEntity<String> deleteUser(HttpEntity<String> entity) {
    try {
      JSONObject json = new JSONObject(entity.getBody());
      String token = entity.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

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
