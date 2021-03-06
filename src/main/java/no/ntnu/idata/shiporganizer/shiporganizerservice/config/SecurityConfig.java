package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import no.ntnu.idata.shiporganizer.shiporganizerservice.auth.JwtAuthenticationFilter;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Class represents the Security Configuration
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserPrincipalService userPrincipalService;
  private final UserService userService;
  private final JWTProperties jwtProperties;

  /**
   * Instantiates a new Security config.
   *
   * @param passwordEncoder      the password encoder
   * @param userPrincipalService the user principal service
   * @param userService          the user service
   * @param jwtProperties        the jwt properties
   */
  public SecurityConfig(PasswordEncoder passwordEncoder,
                        UserPrincipalService userPrincipalService,
                        UserService userService,
                        JWTProperties jwtProperties) {
    this.passwordEncoder = passwordEncoder;
    this.userPrincipalService = userPrincipalService;
    this.userService = userService;
    this.jwtProperties = jwtProperties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService, jwtProperties))
        .authorizeRequests()


        // Permit login and registration
        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .antMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
        // Permit password change/Forgot password
        .antMatchers(HttpMethod.GET, "/api/user/send-verification-code").permitAll()
        .antMatchers(HttpMethod.POST, "/api/user/set-password").permitAll()
        .antMatchers(HttpMethod.POST, "/api/user/check-valid-verification-code").permitAll()

        // USER API
        .antMatchers(HttpMethod.DELETE, "/api/user/delete-user").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.GET, "/api/user/all-users").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/api/user/check-role").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.GET, "/api/user/name").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.GET, "/api/user/departments").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/api/user/edit-user").hasRole("ADMIN")

        // PRODUCTS
        .antMatchers(HttpMethod.GET, "/api/product/inventory").hasAnyRole("USER", "ADMIN")

        // CHECK CONNECTIVITY TO SERVER
        .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
        .antMatchers(HttpMethod.GET, "/connection").permitAll()

        .anyRequest().authenticated()
        .and().httpBasic().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authBuilder) {
    authBuilder.authenticationProvider(daoAuthenticationProvider());
  }

  /**
   * Dao authentication provider dao authentication provider.
   *
   * @return the dao authentication provider
   */
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userPrincipalService);

    return daoAuthenticationProvider;
  }
}
