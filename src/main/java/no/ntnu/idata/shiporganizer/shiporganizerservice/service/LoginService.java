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


@Service
public class LoginService {
  final private UserRepository userRepository;
  final private JWTProperties jwtProperties;
  final private PasswordEncoder passwordEncoder;

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
  public Optional<User> login(String email, String password) {
    // TODO More security features here?
    Optional<User> foundUser = userRepository.findFirstByEmail(email);

    if (foundUser.isPresent()) {
      // TODO Generate token in a better way to include date and check its unique.
      User user = foundUser.get();

      // Checking if password given by client matches password for user.
      if (passwordEncoder.matches(password, user.getPassword())) {
        // Creates new token for user on successful login.
        String token = buildJWT(user.getId(), user.getEmail(), user.getFullname());
        user.setToken(token);
        userRepository.save(user);
        return Optional.of(user);
      } else {
        return Optional.empty();
      }
    }

    return Optional.empty();
  }

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
  private String buildJWT(int id, String email, String name) {
    return JWT.create()
        .withClaim("id", id)
        .withClaim("email", email)
        .withClaim("name", name)
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
        .sign(HMAC512(jwtProperties.getSecretCode().getBytes()));
  }
}
