package com.promineotech.classManagementApi.service;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.classManagementApi.entity.Class;

import com.promineotech.classManagementApi.repository.ClassRepository;

	@Service
	public class ClassServices {
	
	private static final Logger logger = LogManager.getLogger(ClassServices.class);
	
	@Autowired
	private ClassRepository repo;
		
	public Class getClassById(Long id) throws Exception {
		try {
			
			return repo.findOne(id);
				
		} catch(Exception e) {
			
			logger.error("Exception occurred while trying to retrive class: " + id, e);
			throw new Exception("Unable to find class");
		}
	}
		
	public Iterable<Class> getClasses() {
		return repo.findAll();
	}
		
	public Class newClass(Class getClass, Long id) throws Exception {
		getClass.setDateClassCreated(LocalDate.now());
		return repo.save(getClass);
	}
		
	public Class updateClass(Class getClass, Long id) throws Exception {
		try {
			
			Class oldClass = repo.findOne(id);
			oldClass.setClassName(getClass.getClassName());
			oldClass.setClassDescription(getClass.getClassDescription());
			
			return repo.save(oldClass);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update class: " + id, e);
			throw new Exception("Unable to update class");
		}
			
	}

	public void deleteClass(Long id) throws Exception{
		try {
			 repo.delete(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to delete class: " + id, e);
			throw new Exception("Unable to delete student");
		}
		
	}					

}
