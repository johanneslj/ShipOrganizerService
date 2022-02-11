package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  List<User> findAll();

  /**
   * Gets model of user-department relationship for specified user.
   *
   * @param username Username of user to find departments for.
   * @return List of strings, where username and department name is seperated by comma.
   */
  //@Query(value = "EXEC SelectAll @Calltime = 'Users', @Department = '', @Username = :username;", nativeQuery = true)
  //List<String> getUserDepartments(@Param(value = "username") String username);

  // List<UserDepartment> getUserDepartments(User user);

  /**
   * Adds new user with specified arguments to database.
   *
   * @param email    Email/Username of new user.
   * @param password Password (HASH) of new user.
   * @param fullname Full name of new user.
   */
  @Modifying
  @Transactional
  @Query(value = "EXEC HandleUser @Calltime = 'Insert', @Username = :username, @Password = :password, @Fullname = :fullname, @Department = '';", nativeQuery = true)
  void addUser(@Param(value = "username") String email,
               @Param(value = "password") String password,
               @Param(value = "fullname") String fullname);


  /**
   * Deletes user with specified username from database.
   *
   * @param username Username of user to delete.
   */
  @Query(value = "EXEC HandleUser @Calltime = 'Delete', @Username = :username, @Password = '', @Fullname = '', @Department = '';", nativeQuery = true)
  void deleteUser(@Param(value = "username") String username);

  /**
   * Finds first user by passed email.
   *
   * @param email Email of user to find.
   * @return Found user.
   */
  Optional<User> findFirstByEmail(String email);

  /**
   * Finds a user by their username/email and password.
   *
   * @param email    User's email
   * @param password User's password.
   * @return Optional found user.
   */
  Optional<User> findFirstUserByEmailAndPassword(String email, String password);

  /**
   * May find a user by their token.
   *
   * @param token User's token.
   * @return Optional found user.
   */
  Optional<User> findFirstUserByToken(String token);

  //void updateTokenByEmail(String token, String email);
}
