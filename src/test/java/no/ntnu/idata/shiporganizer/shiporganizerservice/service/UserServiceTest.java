package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserDepartmentRepository;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @MockBean
  MailService mailService;

  @MockBean
  UserRepository userRepository;

  @MockBean
  UserDepartmentRepository userDepartmentRepository;

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
  void setup() {
    Mockito
        .doNothing()
        .when(userRepository)
        .addUser(any(), any(), any());
    Mockito
        .doNothing()
        .when(userDepartmentRepository)
        .updateUserDepartment(any(), any());
    Mockito
        .doNothing()
        .when(mailService)
        .sendRegisteredEmail(any());
  }

  @Test
  void addUserShouldReturnTrue() {
    Mockito
        .when(userRepository.findFirstByEmail("one@address.com"))
        .thenReturn(Optional.of(userOne));
    Mockito.when(userRepository.save(any(User.class))).thenReturn(userOne);
    assertTrue(underTest.registerAndGetSuccess(userOne, Arrays.asList(dep1, dep2, dep3)));
  }

  @Test
  void getAllUsers() {
    Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(userOne, userTwo, userThree));

    List<User> users = underTest.getAllUsers();
    assertEquals(3, users.size());
  }

  @Test
  void shouldGetByEmail() {
    Mockito
        .when(userRepository.findFirstByEmail("one@address.com"))
        .thenReturn(Optional.of(userOne));
    User result = underTest.getByEmail("one@address.com").orElse(new User());
    assertEquals(userOne, result);
  }

  @Test
  void shouldGetEmptyOptionalByEmail() {
    Optional<User> resultOptional = underTest.getByEmail("invalid email");
    assertTrue(resultOptional.isEmpty());
  }
}