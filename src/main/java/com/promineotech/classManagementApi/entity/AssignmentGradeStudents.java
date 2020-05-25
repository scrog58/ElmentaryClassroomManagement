package com.promineotech.classManagementApi.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class AssignmentGradeStudents {

	private Long id;
	private Long classid;
	private Long assignmentid;
	private Long studentid;
	private Double assignmentgrade;
	private Double assignmentTotalGradePoint;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getClassid() {
		return classid;
	}

	public void setClassid(Long classid) {
		this.classid = classid;
	}

	public Long getAssignmentid() {
		return assignmentid;
	}
	
	public void setAssignmentid(Long assignmentid) {
		this.assignmentid = assignmentid;
	}
	
	public Long getStudentid() {
		return studentid;
	}
	public void setStudentid(Long studentid) {
		this.studentid = studentid;
	}
	
	public Double getAssignmentGrade() {
		return assignmentgrade;
	}
	
	public void setAssignmentGrade(Double assignmentgrade) {
		this.assignmentgrade = assignmentgrade;
	}

	public Double getAssignmentTotalGradePoint() {
		return assignmentTotalGradePoint;
	}

	public void setAssignmentTotalGradePoint(Double assignmentTotalGradePoint) {
		this.assignmentTotalGradePoint = assignmentTotalGradePoint;
	}
	
	
}
