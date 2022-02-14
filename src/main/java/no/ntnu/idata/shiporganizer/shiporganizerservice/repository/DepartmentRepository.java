package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

  Optional<Department> findDepartmentByName(String departmentName);

  Optional<Department> findDepartmentById(int id);
}
