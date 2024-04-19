package com.diSmartCoder.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diSmartCoder.ems.exception.ResourceNotFoundException;
import com.diSmartCoder.ems.model.Employee;
import com.diSmartCoder.ems.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	// Get all employees

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// Create employee rest API

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	// Get employee by id rest API

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists with id " + id));

		return ResponseEntity.ok(employee);

	}

	// Update employee rest API

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
												   @RequestBody Employee employeeDetails) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists with id " + id));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee = employeeRepository.save(employee);

		return ResponseEntity.ok(updatedEmployee);
	}

	// Delete employee rest API
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee  = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " +id));
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
