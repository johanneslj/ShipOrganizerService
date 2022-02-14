package no.ntnu.idata.shiporganizer.shiporganizerservice.auth;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.auth0.jwt.JWT;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.JWTProperties;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Authentication filter for security config.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

  private final UserService userService;
  private final JWTProperties jwtProperties;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                 UserService userService,
                                 JWTProperties jwtProperties) {
    super(authenticationManager);
    this.userService = userService;
    this.jwtProperties = jwtProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {

    // Read authorization header, where JWT token should be located
    String header = request.getHeader(jwtProperties.getHeaderString());
    // If header does not contain bearer or is null, pass to Spring impl and exit
    if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
      chain.doFilter(request, response);
      return;
    }

    // If there is a header, try to get the user principal from the database and authorize
    Authentication authentication = getUsernamePasswordAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Pass on the filtering
    chain.doFilter(request, response);
  }

  private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
    // Get token string from header.
    String token = request.getHeader(jwtProperties.getHeaderString())
        .replace("Bearer ", "").trim();

    // Get user optional by token from user service.
    Optional<User> user = userService.getByToken(token);

    // Verify token is not expired.
    if (JWT.require(HMAC512(jwtProperties.getSecretCode().getBytes())).build().verify(token)
        .getExpiresAt().before(new Date())) {
      return null;
    }

    // If user with token exists, return authorities.
    if (user.isPresent()) {
      UserPrincipal principal = new UserPrincipal(user.get(), userService);
      return new UsernamePasswordAuthenticationToken(principal, null,
          principal.getAuthorities());
    }
    return null;

  }
}
