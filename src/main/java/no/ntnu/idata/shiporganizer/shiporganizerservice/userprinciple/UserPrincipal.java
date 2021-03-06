package no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Provides UserDetails for the user principle.
 */
public class UserPrincipal implements UserDetails {
  // The User
  private final User user;
  // The user service
  private final UserService userService;
  // User is set to rights = 0
  private final int USER = 0;
  // Admin is set to rights = 1
  private final int ADMIN = 1;

  /**
   * Instantiates a User principal.
   *
   * @param user        the user
   * @param userService the user service
   */
  public UserPrincipal(User user, UserService userService) {
    this.user = user;
    this.userService = userService;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    // Gets the rights/authorities for each department the user is a member of.
    userService.getDepartments(user).forEach(d -> {
      int rights = d.getRights();
      switch (rights) {
        case USER:
          authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
          break;
        case ADMIN:
          authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
          break;
      }
    });

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    // If mail does not exist an empty user object is created.
    User user = userService.getByEmail(this.user.getEmail()).orElseGet(User::new);

    // Checks that the password is set and not empty.
    // Password is empty when admin has registered the user and the user has not yet set a password.
    return user.getPassword().length() > 0;
  }
}
