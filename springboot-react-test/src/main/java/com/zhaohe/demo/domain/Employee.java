package com.zhaohe.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
@Data//autogenerate getters, setters, constructors, toString, hash, equals, and other things
@Entity
public class Employee {
	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String des;
	private Employee(){}
	public Employee(String firstName, String lastName, String des) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.des = des;
	}
	
	
}
