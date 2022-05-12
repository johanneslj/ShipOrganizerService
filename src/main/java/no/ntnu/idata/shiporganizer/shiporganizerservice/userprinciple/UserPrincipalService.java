package no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple;

import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type User principal service.
 */
@Service
public class UserPrincipalService implements UserDetailsService {

  final private UserRepository userRepository;
  final private UserService userService;

  /**
   * Instantiates a  User principal service.
   *
   * @param userRepository the user repository
   * @param userService    the user service
   */
  public UserPrincipalService(
      UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (!userRepository.findFirstByEmail(username).isPresent()) {
      throw new UsernameNotFoundException(username);
    }

    return new UserPrincipal(userRepository.findFirstByEmail(username).get(), userService);
  }
}
