package no.ntnu.idata.shiporganizer.shiporganizerservice.auth;

import no.ntnu.idata.shiporganizer.shiporganizerservice.service.LoginService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple.UserPrincipalService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {

  final private LoginService loginService;

  public AuthenticationProvider(LoginService loginService,
                                UserPrincipalService userPrincipalService) {
    this.loginService = loginService;

    super.setUserDetailsService(userPrincipalService);
  }
}
