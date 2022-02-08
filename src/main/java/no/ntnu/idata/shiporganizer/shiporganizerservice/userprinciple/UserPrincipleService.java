package no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple;

import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleService implements UserDetailsService {

  final private UserRepository userRepository;
  final private UserService userService;

  public UserPrincipleService(
      UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (!userRepository.findFirstByEmail(username).isPresent()) {
      throw new UsernameNotFoundException(username);
    }

    return new UserPrinciple(userRepository.findFirstByEmail(username).get(), userService);
  }
}
