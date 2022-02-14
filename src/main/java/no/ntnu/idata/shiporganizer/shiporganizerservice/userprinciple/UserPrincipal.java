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

  private final User user;
  private final UserService userService;

  private final int USER = 0;
  private final int ADMIN = 1;

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
    return userService.getByEmail(user.getEmail()).isPresent();
  }
}
