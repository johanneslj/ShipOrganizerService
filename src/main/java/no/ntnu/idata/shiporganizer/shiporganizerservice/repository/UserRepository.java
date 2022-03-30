package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
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
   * Adds new user with specified arguments to database.
   *
   * @param email    Email/Username of new user.
   * @param password Password (HASH) of new user.
   * @param fullname Full name of new user.
   */
  @Modifying
  @Transactional
  @Query(value = "EXEC HandleUser @Calltime = 'Insert', @Username = :username, @Password = :password, @Fullname = :fullname, @Department = '', @OldEmail = '';", nativeQuery = true)
  void addUser(@Param(value = "username") String email,
               @Param(value = "password") String password,
               @Param(value = "fullname") String fullname);


  /**
   * Deletes user with specified username from database.
   *
   * @param username Username of user to delete.
   */
  @Modifying
  @Transactional
  @Query(value = "EXEC HandleUser @Calltime = 'Delete', @Username = :username, @Password = '', @Fullname = '', @Department = '', @OldEmail = '';", nativeQuery = true)
  void deleteUser(@Param(value = "username") String username);

  @Modifying
  @Transactional
  @Query(value = "EXEC HandleUser @Calltime = 'Update', @Username = :username, @Password='', @Fullname = :fullname, @Department = '', @OldEmail = :oldEmail", nativeQuery = true)
  void editUser(@Param(value = "username") String username,
                @Param(value = "fullname") String fullname,
                @Param(value = "oldEmail") String oldEmail);

  /**
   * Finds first user by passed email.
   *
   * @param email Email of user to find.
   * @return Found user.
   */
  Optional<User> findFirstByEmail(String email);

  /**
   * May find a user by their token.
   *
   * @param token User's token.
   * @return Optional found user.
   */
  Optional<User> findFirstUserByToken(String token);
}
