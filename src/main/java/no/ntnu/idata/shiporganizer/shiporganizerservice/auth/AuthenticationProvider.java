package no.ntnu.idata.shiporganizer.shiporganizerservice.auth;

import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.LoginService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple.UserPrincipleService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {

  final private LoginService loginService;

  public AuthenticationProvider(LoginService loginService,
                                UserPrincipleService userPrincipleService) {
    this.loginService = loginService;

    super.setUserDetailsService(userPrincipleService);
  }
}
