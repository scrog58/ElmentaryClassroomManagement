package com.promineotech.classManagementApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.classManagementApi.entity.Parent;

public interface ParentRepository extends CrudRepository<Parent, Long>{

	public Parent findByUsername(String username);

}
