package com.promineotech.classManagementApi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import com.promineotech.classManagementApi.entity.Parent;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.repository.ParentRepository;
import com.promineotech.classManagementApi.util.AccountLevel;

@Service
public class ParentServices {
private static final Logger logger = LogManager.getLogger(ParentServices.class);
	
	@Autowired
	private ParentRepository repo;
	
	
	public Parent getParentById(Long id) throws Exception {
		try {
			return repo.findOne(id);
			
		} catch(Exception e) {
			logger.error("Can't find parent id: " + id, e);
			throw e;
		}
	}
	
	public Iterable<Parent> getParents() {
		return repo.findAll();
	}
	
	public Parent createParent(Parent parent) {
		parent.setPassword(passwordHash(parent.getPassword()));
		parent.setLevel(AccountLevel.PARENT);
		return repo.save(parent);
	}
	
	public Parent login(Parent parent) throws Exception {
		Parent foundParent = repo.findByUsername(parent.getUsername());
		String password = parent.getPassword();
		//equal is they are both string foundParent.getPassword().equals(parent.getPassword())
		if(foundParent != null && BCrypt.checkpw(password, foundParent.getPassword())) {
			return foundParent;
		} else {
			throw new Exception("Invalid username or password");
		}
	}
	
	public Parent updateParent(Parent parent, Long id) throws Exception {
		try {
			Parent oldParent = repo.findOne(id);
			oldParent.setFirstName(parent.getFirstName());
			oldParent.setLastName(parent.getLastName());
			oldParent.setAddress(parent.getAddress());
			oldParent.setState(parent.getState());
			oldParent.setZip(parent.getZip());
			oldParent.setPhoneNumber(parent.getPhoneNumber());
			oldParent.setEmail(parent.getEmail());
			oldParent.setLevel(AccountLevel.PARENT);
			return repo.save(oldParent);
		} catch(Exception e) {
			logger.error("Can't update parent id: " + id, e);
			throw new Exception("Unable to update parent information");
		}
	}
	
	public Parent updateParentUserName(Parent parent, Long id) throws Exception {
		try {
			Parent oldParent = repo.findOne(id);
			oldParent.setUsername(parent.getUsername());
			oldParent.setPassword(passwordHash(parent.getPassword()));
			return repo.save(oldParent);
		} catch(Exception e) {
			logger.error("Can't update parent id: " + id, e);
			throw new Exception("Unable to update parent username or password");
		}
	} 
	
	public void deleteParent(Long id) throws Exception {
		try {
			repo.delete(id);
		} catch(Exception e) {
			logger.error("Can't delete parent id: "+id,e);
			throw new Exception("Unable to delete parent");
		}
	}
	
	private String passwordHash(String password) {
		
		String pass = password.toString();
		String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
		
		return hash;
		
	}

}
