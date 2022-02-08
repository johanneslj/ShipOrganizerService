package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
