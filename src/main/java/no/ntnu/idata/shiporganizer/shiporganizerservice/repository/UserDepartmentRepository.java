package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository responsible for the connection to
 * execute all user department procedures in the database.
 */
@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Integer> {

  /**
   * Gets a list of all the connections the user has to the department table
   * @param userID The id of the user
   * @return List of Department connections for the user
   */
  List<UserDepartment> getUserDepartmentsByUserID(int userID);

  /**
   * Updates departments of user to new specified departments.
   *
   * @param username    Email/Username of user to update departments for.
   * @param departments String of departments as comma-seperated values without space, i.e. "bridge,deck"
   */
  @Modifying
  @Transactional
  @Query(value = "Call HandleUser('UpdateDepartment',:username,'','',:departments,'')", nativeQuery = true)
  void updateUserDepartment(@Param(value = "username") String username,
                            @Param(value = "departments") String departments);
}
