package com.promineotech.classManagementApi.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Class;

@Entity
public class AssignmentsGrades {
	
	private Long id;
	private String description;
	private LocalDate date;
	private double grade;
	
	@JsonIgnore 
	private Class classroom;
	
	@JsonIgnore 
	private Employees employee;
	
	@JsonIgnore 
	private Parent parent;
	
	@JsonIgnore 
	private Set<Student> student;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "classId")
	public Class getClassroom() {
		return classroom;
	}

	public void setClassroom(Class classroom) {
		this.classroom = classroom;
	}

	@ManyToOne
	@JoinColumn(name = "employeeId")
	public Employees getTeacher() {
		return employee;
	}
	
	public void setTeacher(Employees employee) {
		this.employee = employee;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "parentId")
	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assignmentgrades_student"
			,joinColumns = @JoinColumn(name = "studentId", referencedColumnName = "id")
			,inverseJoinColumns = @JoinColumn(name = "assignmentId", referencedColumnName = "id"))
	public Set<Student> getStudent() {
		return student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}

}
