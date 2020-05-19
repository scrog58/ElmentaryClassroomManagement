package com.promineotech.classManagementApi.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;



import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.repository.EmployeesRepository;
import com.promineotech.classManagementApi.util.AccountLevel;


@Service
public class EmployeesServices {
	
private static final Logger logger = LogManager.getLogger(EmployeesServices.class);
	
	@Autowired
	private EmployeesRepository repo;
	
	public Employees getEmployeesById(Long id) throws Exception {
		try {
			return repo.findOne(id);
			
		} catch(Exception e) {
			logger.error("Can't find employee id: " + id, e);
			throw e;
		}
	}
	
	public Iterable<Employees> getEmployees() {
		return repo.findAll();
	}
	
	public Iterable<Employees> getListEmployees() {
		return repo.findAll();
	}
	
	public Employees createEmployee(Employees employee) {
		employee.setPassword(passwordHash(employee.getPassword()));
		return repo.save(employee);
	}
	
	public Employees login(Employees employee) throws Exception {
		//AuthenticationService auth = new AuthenticationService();
		
		Employees foundEmp = repo.findByUsername(employee.getUsername());
		
		String password = employee.getPassword();
		//foundEmp != null && foundEmp.getPassword().equals(employee.getPassword())
		if(foundEmp != null && BCrypt.checkpw(password, foundEmp.getPassword())) {
			
			//auth.setJwet(generateToken(foundEmp.getUsername()));
			return foundEmp;
		} else {
			throw new Exception("Invalid username or password");
		}
	}
	
	public Employees updateEmployee(Employees employee, Long id) throws Exception {
		try {
			Employees oldEmp = repo.findOne(id);
			oldEmp.setFirstName(employee.getFirstName());
			oldEmp.setLastName(employee.getLastName());
			oldEmp.setTitle(employee.getTitle());
			oldEmp.setAddress(employee.getAddress());
			oldEmp.setState(employee.getState());
			oldEmp.setZip(employee.getZip());
			oldEmp.setEmail(employee.getEmail());
			oldEmp.setAccountLevel(AccountLevel.TEACHER);
			return repo.save(oldEmp);
		} catch(Exception e) {
			logger.error("Can't update employee id: " + id, e);
			throw new Exception("Unable to update employee information");
		}
	}
	
	public Employees updateEmployeeUserName(Employees employee, Long id) throws Exception {
		try {
			Employees oldEmp = repo.findOne(id);
			oldEmp.setUsername(employee.getUsername());
			oldEmp.setPassword(passwordHash(employee.getPassword()));
			return repo.save(oldEmp);
		} catch(Exception e) {
			logger.error("Can't update employee id: " + id, e);
			throw new Exception("Unable to update employee username or password");
		}
	} 
	
	public void deleteEmployee(Long id) throws Exception {
		try {
			repo.delete(id);
		} catch(Exception e) {
			logger.error("Can't delete employee id: "+id,e);
			throw new Exception("Unable to delete employee");
		}
	}
	
	private String passwordHash(String password) {
		
		String pass = password.toString();
		String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
		
		return hash;
		
	}
	

}
