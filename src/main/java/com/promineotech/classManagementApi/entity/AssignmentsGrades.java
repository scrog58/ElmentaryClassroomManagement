package com.promineotech.classManagementApi.entity;

import java.time.LocalDate;
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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.classManagementApi.entity.Student;
import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.entity.Classs;

@Entity
public class AssignmentsGrades {
	
	private Long id;
	private String description;
	private LocalDate date;
	private double totalGradeAmount;
	
	@JsonIgnore 
	private Classs classroom;
	
	@JsonIgnore 
	private Parent parent;
	
	@JsonIgnore 
	private Set<Student> students;
	
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
	
	public double getTotalGradeAmount() {
		return totalGradeAmount;
	}

	public void setTotalGradeAmount(double totalGradeAmount) {
		this.totalGradeAmount = totalGradeAmount;
	}

	@ManyToOne
	@JoinColumn(name = "classs")
	public Classs getClassroom() {
		return classroom;
	}

	public void setClassroom(Classs classroom) {
		this.classroom = classroom;
	}
	
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assignment_student"
			,joinColumns = @JoinColumn(name = "assignmentid", referencedColumnName = "id")
			,inverseJoinColumns = @JoinColumn(name = "studentid", referencedColumnName = "id"))
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
