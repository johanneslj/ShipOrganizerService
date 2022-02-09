package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.microsoft.sqlserver.jdbc.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.JWTProperties;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class LoginService {
  final private UserRepository userRepository;
  final private JWTProperties jwtProperties;

  public LoginService(UserRepository userRepository, JWTProperties jwtProperties) {
    this.userRepository = userRepository;
    this.jwtProperties = jwtProperties;
  }

  public Optional<User> login(String email, String password) {
    // TODO More security features here?
    Optional<User> foundUser = userRepository.findFirstUserByEmailAndPassword(email, password);

    if (foundUser.isPresent()) {
      // TODO Generate token in a better way to include date and check its unique.
      User user = foundUser.get();

      String token = buildJWT(user.getId(), user.getEmail(), user.getFullname());

      user.setToken(token);
      userRepository.save(user);
      return Optional.of(user);
    }

    return Optional.empty();
  }

  public Optional<User> findByToken(String token) {
    return userRepository.findFirstUserByToken(token);
  }

  private String buildJWT(int id, String email, String name) {
    return com.auth0.jwt.JWT.create()
        .withClaim("id", id)
        .withClaim("email", email)
        .withClaim("name", name)
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
        .sign(HMAC512(jwtProperties.getSecretCode().getBytes()));
  }
}
