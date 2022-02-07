package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  List<User> findAll();

  /**
   * Gets model of user-department relationship for specified user.
   *
   * @param username Username of user to find departments for.
   * @return List of strings, where username and department name is seperated by comma.
   */
  @Query(value = "EXEC SelectAll @Calltime = 'Users', @Department = '', @Username = :username;", nativeQuery = true)
  List<String> getUserDepartments(@Param(value = "username") String username);

  /**
   * Adds new user with specified arguments to database.
   *
   * @param email    Email/Username of new user.
   * @param password Password (HASH) of new user.
   * @param fullname Full name of new user.
   */
  @Query(value = "EXEC HandleUser @Calltime = 'Insert', @Username = :username, @Password = :password, @Fullname = :fullname, @Department = '';", nativeQuery = true)
  void addUser(@Param(value = "username") String email,
               @Param(value = "password") String password,
               @Param(value = "fullname") String fullname);

  /**
   * Updates departments of user to new specified departments.
   *
   * @param email       Email/Username of user to update departments for.
   * @param departments String of departments as comma-seperated values without space, i.e. "bridge,deck"
   */
  @Query(value = "EXEC HandleUser @Calltime = 'UpdateDepartment', @Username = :username, @Password = '', @Fullname = '', @Department = :departments;", nativeQuery = true)
  @Modifying
  void updateUserDepartment(@Param(value = "username") String email,
                            @Param(value = "departments") String departments);

  /**
   * Deletes user with specified username from database.
   *
   * @param username Username of user to delete.
   */
  @Query(value = "EXEC HandleUser @Calltime = 'Delete', @Username = :username;", nativeQuery = true)
  void deleteUser(@Param(value = "username") String username);

  /**
   * Finds a user by their username/email and password. Used for login.
   * TODO implement with procedures?
   *
   * @param email    Users's email
   * @param password
   * @return Optional found user.
   */
  Optional<User> findFirstUserByEmailAndPassword(String email, String password);

  /**
   * May find a user by their token.
   *
   * @param token User's token.
   * @return Optional found user.
   */
  //Optional<User> findUserByToken(String token);

  // TODO Remove this method. Only for testing purposes.
  Optional<User> findFirst();


}
