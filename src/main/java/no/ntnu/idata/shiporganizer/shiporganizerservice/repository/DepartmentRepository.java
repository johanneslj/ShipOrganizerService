package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.Optional;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Department repository.
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

  /**
   * Find department by name.
   *
   * @param departmentName the department name
   * @return the optional
   */
  Optional<Department> findDepartmentByName(String departmentName);

  /**
   * Find department by id.
   *
   * @param id the id
   * @return the optional
   */
  Optional<Department> findDepartmentById(int id);

  /**
   * Gets list of all departments
   * @return List of all departments
   */
  List<Department> findAll();
}
