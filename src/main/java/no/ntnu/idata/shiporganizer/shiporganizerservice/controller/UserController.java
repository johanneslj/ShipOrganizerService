package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
      String usernameToDelete = json.getString("username");

      List<String> authorizationHeader = entity.getHeaders().get(HttpHeaders.AUTHORIZATION);
      String token = "";

      // Get the bearer token if it exists.
      if (authorizationHeader != null) {
        Iterator<String> iterator = authorizationHeader.iterator();

        while (iterator.hasNext() && token.length() < 1) {
          String authHeader = iterator.next();

          if (authHeader.startsWith("Bearer ")) {
            token = authHeader.split(" ")[1];
          }
        }

        Optional<User> userOptional = userService.getByToken(token);

        // Check if requesting user exists.
        if (userOptional.isPresent()) {
          // Check if requesting user is admin or is deleting self.
          if (userService.isUserAdmin(userOptional.get())
              || userOptional.get().getEmail().equals(usernameToDelete)) {

            userService.deleteUser(json.getString("username"));

            return ResponseEntity.ok("User deleted.");
          }
        }
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
