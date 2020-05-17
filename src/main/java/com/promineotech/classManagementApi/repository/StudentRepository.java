package com.promineotech.classManagementApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.classManagementApi.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
