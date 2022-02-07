package no.ntnu.idata.shiporganizer.shiporganizerservice.auth;

import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.LoginService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  LoginService loginService;

  public AuthenticationProvider(LoginService loginService) {
    this.loginService = loginService;
  }


  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String userName,
                                     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {

    Object token = usernamePasswordAuthenticationToken.getCredentials();
    return Optional
        .ofNullable(token)
        .map(String::valueOf)
        .flatMap(loginService::findByToken)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Cannot find user with authentication token=" + token));
  }
}
