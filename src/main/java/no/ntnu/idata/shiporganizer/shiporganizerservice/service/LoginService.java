package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.auth0.jwt.JWT;
import java.util.Date;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.JWTProperties;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Class representing the Login service.
 * This service is a connection between the Controller and Repository
 */
@Service
public class LoginService {
  final private UserRepository userRepository;
  final private JWTProperties jwtProperties;
  final private PasswordEncoder passwordEncoder;

  /**
   * Instantiates a LoginService
   * @param userRepository the UserRepository interface
   * @param jwtProperties the JWTProperties class
   * @param passwordEncoder the PasswordEncoder class
   */
  public LoginService(UserRepository userRepository,
                      JWTProperties jwtProperties,
                      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtProperties = jwtProperties;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Gets optional with user and sets new token on correct email and password combination.
   * Empty optional on failure.
   *
   * @param email    User's email.
   * @param password User's password.
   * @return Optional of user on success, empty optional on failure.
   */
  public Optional<User> loginAndGetUserOptional(String email, String password) {
    Optional<User> userOptional = userRepository.findFirstByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return checkCorrectPasswordAndSetNewTokenForUser(password, user)
          ? Optional.of(user)
          : Optional.empty();
    }
    return Optional.empty();
  }

  /**
   * Checks if the given user password matches with the one stored in the user object
   * @param password Users password
   * @param user the user object
   * @return True if password matches and False if not
   */
  private boolean checkCorrectPasswordAndSetNewTokenForUser(String password, User user) {
    if (passwordEncoder.matches(password, user.getPassword())) {
      setNewTokenForUser(user);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  /**
   * Setts a new token for the given user
   * @param user the user object
   */
  private void setNewTokenForUser(User user) {
    String newToken = buildJWT(user.getId(), user.getEmail(), user.getFullname());
    user.setToken(newToken);
  }

  /**
   * Finds a user based on the token given.
   * @param token the token used in search
   * @return if user is found the user is returned.
   */
  public Optional<User> findByToken(String token) {
    return userRepository.findFirstUserByToken(token);
  }

  /**
   * Builds a JWT with user's id, email, and name as claims.
   * With expiration time set in JWT properties.
   *
   * @param id    User's ID.
   * @param email User's email.
   * @param name  User's full name.
   * @return String containing the JWT token.
   */
  public String buildJWT(int id, String email, String name) {
    return JWT.create()
        .withClaim("id", id)
        .withClaim("email", email)
        .withClaim("name", name)
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
        .sign(HMAC512(jwtProperties.getSecretCode().getBytes()));
  }
}
