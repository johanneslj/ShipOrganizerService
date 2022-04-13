package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import no.ntnu.idata.shiporganizer.shiporganizerservice.auth.JwtAuthenticationFilter;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.SecurityConfig;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  MockMvc mvc;

  @MockBean
  UserService userService;

  final private User user1 = new User(1, "token1", "Name One", "mail@adress.com", "password");
  final private User user2 = new User(2, "token2", "Name Two", "mail@adress.com", "password");
  final private User user3 = new User(3, "token3", "Name Three", "mail@adress.com", "password");

  @Test
  void getAllPublicUsers() throws Exception {
    Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2, user3));

    mvc.perform(get("/api/user/all-users"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$", Matchers.hasSize(3)));
  }
}