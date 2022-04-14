package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @MockBean
  UserRepository userRepository;

  @Autowired
  @InjectMocks
  UserService underTest;

  final User userOne = new User(1, "token1", "Name One", "one@address.com", "password");
  final User userTwo = new User(2, "token2", "Name Two", "two@address.com", "password");
  final User userThree = new User(3, "token3", "Name Three", "three@address.com", "password");

  final Department dep1 = new Department(1, "Bridge", 1);
  final Department dep2 = new Department(2, "Deck", 0);
  final Department dep3 = new Department(3, "Motor", 0);

  @BeforeEach
  void addTestUsers() {
    underTest.registerAndGetSuccess(userOne, Arrays.asList(dep1, dep2, dep3));
    underTest.registerAndGetSuccess(userTwo, Arrays.asList(dep1, dep2));
    underTest.registerAndGetSuccess(userThree, Arrays.asList(dep2, dep3));
  }

  @Test
  void getAllUsers() {
    List<User> users = underTest.getAllUsers();
    assertEquals(3, users.size());
  }

  @Test
  void shouldGetByEmail() {
    User result = underTest.getByEmail("one@address.com").orElse(new User());
    assertEquals(userOne, result);
  }

  @Test
  void shouldGetEmptyOptionalByEmail() {
    Optional<User> resultOptional = underTest.getByEmail("invalid email");
    assertTrue(resultOptional.isEmpty());
  }
}