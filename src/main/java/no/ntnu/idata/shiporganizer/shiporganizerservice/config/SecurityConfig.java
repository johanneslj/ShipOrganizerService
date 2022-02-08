package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

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

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserPrincipalService userPrincipalService;

  public SecurityConfig(PasswordEncoder passwordEncoder,
                        UserPrincipalService userPrincipalService) {
    this.passwordEncoder = passwordEncoder;
    this.userPrincipalService = userPrincipalService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/user/delete-user").authenticated()
        .anyRequest().authenticated()
        .and()
        .logout()
        .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authBuilder) {
    authBuilder.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userPrincipalService);

    return daoAuthenticationProvider;
  }
}
