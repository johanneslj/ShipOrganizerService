package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
