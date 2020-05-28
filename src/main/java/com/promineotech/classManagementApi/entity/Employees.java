package com.promineotech.classManagementApi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.promineotech.classManagementApi.util.AccountLevel;


@Entity
public class Employees {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String title;
	private String address;
	private String state;
	private String zip;
	private String email;
	private Set<Classs> listClassroom;
	
	private AccountLevel level;
	
	private String username;
	private String password;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(unique=true)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToMany(mappedBy = "teacherName")
	public Set<Classs> getListClassroom() {
		return listClassroom;
	}

	public void setListClassroom(Set<Classs> listClassroom) {
		this.listClassroom = listClassroom;
	}

	public AccountLevel getAccountLevel() {
		return level;
	}
	
	public void setAccountLevel(AccountLevel level) {
		this.level = level;
	}
	
	@Column(unique=true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
