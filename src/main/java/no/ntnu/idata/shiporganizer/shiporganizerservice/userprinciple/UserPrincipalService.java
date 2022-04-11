package no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple;

import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

  final private UserService userService;

  public UserPrincipalService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (userService.getByEmail(username).isEmpty()) {
      throw new UsernameNotFoundException(username);
    }

    return new UserPrincipal(userService.getByEmail(username).get(), userService);
  }
}
