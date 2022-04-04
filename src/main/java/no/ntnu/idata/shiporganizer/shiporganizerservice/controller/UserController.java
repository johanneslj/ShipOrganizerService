package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.PublicUserModel;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
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
   * Gets a list of all users with non-sensitive information.
   */
  @GetMapping("/all-users")
  public ResponseEntity<List<PublicUserModel>> getAllPublicUsers() {
    return ResponseEntity.ok(
        userService.getAllUsers().stream()
            .map(user -> new PublicUserModel(user.getFullname(),
                user.getEmail())) //TODO get departments they have access to as well
            .collect(Collectors.toList()));
  }

  /**
   * Uses information about the user to enable editing a pre-existing user
   *
   * @param entity details about the user
   * @return Response of ok if successful else an error response
   */
  @PostMapping("/edit-user")
  public ResponseEntity<String> editUser(HttpEntity<String> entity) {
    try {
      JSONObject json = new JSONObject(entity.getBody());
      System.out.println(json);
      String oldEmail = json.getString("oldEmail");
      String newEmail = json.getString("newEmail");
      String name = json.getString("name");
      List<Department> departments = getDepartmentsFromJson(json);
      User user = new User(name, newEmail);
      userService.editUser(user, oldEmail, departments);
      return ResponseEntity.ok().build();
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Gets the name of the user who owns the token.
   *
   * @param entity Http Entity containing user's bearer token in header.
   * @return 200 OK with name in body.
   */
  @GetMapping("/name")
  public ResponseEntity<String> getName(HttpEntity<String> entity) {
    return ResponseEntity.ok(
        userService
            .getByToken(getBearerTokenFromHttpEntity(entity))
            .orElseGet(() -> new User("", ""))
            .getFullname());
  }

  /**
   * Gets the departments of the user who owns the token.
   *
   * @param entity HTTP entity containing user's token.
   * @return 200 OK with departments as JSON array in body
   */
  @GetMapping("/departments")
  public ResponseEntity<List<Department>> getDepartments(HttpEntity<String> entity) {
    User user = userService.getByToken(getBearerTokenFromHttpEntity(entity)).orElseGet(User::new);
    return ResponseEntity.ok(userService.getDepartments(user));
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
      String token = getBearerTokenFromHttpEntity(entity);
      Optional<User> userOptional = userService.getByToken(token);
      if (userOptional.isPresent()) {
        if (userService.isUserAdmin(token)
            || userOptional.get().getEmail().equals(usernameToDelete)) {
          userService.deleteUser(json.getString("username"));
          return ResponseEntity.ok("User deleted.");
        }
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Makes the server send a verification code to given email to set new password.
   *
   * @param email User's email address.
   * @return Response Entity 200 OK on success.
   */
  @GetMapping("/send-verification-code")
  public ResponseEntity<String> sendVerificationCode(@Param("email") String email) {
    if (userService.sendNewPasswordEmail(email)) {
      return ResponseEntity.ok("Verification code sent.");
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Sets a new password for the user using the received verification code.
   *
   * @param http HTTP entity.
   * @return Response Entity.
   */
  @PostMapping("/set-password")
  public ResponseEntity<String> setNewPassword(HttpEntity<String> http) {
    try {
      JSONObject json = new JSONObject(http.getBody());
      String verificationCode = json.getString("code");
      String email = json.getString("email");
      String password = json.getString("password");
      boolean isSuccess =
          userService.setNewPasswordWithVerificationCode(email, verificationCode, password);
      if (isSuccess) {
        return ResponseEntity.ok("Password changed.");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
      }
    } catch (JSONException e) {
      return ResponseEntity.badRequest().build();
    }
  }

    /**
     * Checks if verification code is valid.
     *
     * @return ResponseEntity 200 OK if valid.
     */
    @PostMapping("/check-valid-verification-code")
    public ResponseEntity<String> checkValidVerificationCode(HttpEntity<String> httpEntity) {
        try {
            JSONObject json = new JSONObject(httpEntity.getBody());
            String verificationCode = json.getString("code");
            String email = json.getString("email");
            if(userService.checkValidVerificationCode(email, verificationCode)) {
                return ResponseEntity.ok("Verification code valid.");
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (JSONException e) {
            return ResponseEntity.badRequest().build();
        }
    }

  /**
   * Returns the role of the user. ADMIN or USER.
   * <p>
   * Security configuration is so that only authorized users, or admins can access this endpoint.
   *
   * @param entity Http Entity.
   * @return 200 OK with role in body.
   */
  @GetMapping("/check-role")
  public ResponseEntity<String> checkRole(HttpEntity<String> entity) {
    return userService.isUserAdmin(getBearerTokenFromHttpEntity(entity))
        ? ResponseEntity.ok("ADMIN")
        : ResponseEntity.ok("USER");
  }

  /**
   * Gets the Bearer token from the HTTP headers if it exists.
   *
   * @param entity HTTP Entity to get bearer from.
   * @return Token, or empty string if not found
   */
  private String getBearerTokenFromHttpEntity(HttpEntity<String> entity) {
    List<String> authorizationHeader = entity.getHeaders().get(HttpHeaders.AUTHORIZATION);
    String token = "";
    if (authorizationHeader != null) {
      Iterator<String> iterator = authorizationHeader.iterator();
      while (iterator.hasNext() && token.length() < 1) {
        String authHeader = iterator.next();
        if (authHeader.startsWith("Bearer ")) {
          token = authHeader.split(" ")[1];
        }
      }
    }
    return token;
  }

  private List<Department> getDepartmentsFromJson(JSONObject json) throws JSONException {
    List<Department> departments = new ArrayList<>();
    JSONArray jsonArray = json.getJSONArray("departments");
    for (int i = 0; i < jsonArray.length(); i++) {
      departments.add(new Department(jsonArray.getString(i)));
    }
    return departments;
  }
}
