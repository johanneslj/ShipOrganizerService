package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Department;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The type Department controller. Handles all the direct api calls and sends the variables to the repository
 * for the correct handling
 */
@Controller
@RequestMapping(value ="/department")
@Transactional
public class DepartmentController {

	/**
	 * The Department repository.
	 */
	DepartmentRepository departmentRepository;

	/**
	 * Instantiates a new Department controller.
	 *
	 * @param DepartmentRepository the department repository
	 */
	public DepartmentController(DepartmentRepository DepartmentRepository) {
		departmentRepository = DepartmentRepository;
	}

	/**
	 * Gets departments.
	 *
	 * @return the departments
	 */
	@GetMapping(path = "/all")
	public List<Department> getDepartments() {
		return departmentRepository.findAll();
	}

}
