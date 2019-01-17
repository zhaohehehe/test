package com.zhaohe.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//CommandLineRunner:gets run after all the beans are created and registered.
public class DatabaseLoader implements CommandLineRunner {
	private final EmployeeRepository repository;
	
	@Autowired
	public DatabaseLoader(EmployeeRepository repository) {
		this.repository = repository;
	}
	

	@Override
	public void run(String... arg0) throws Exception {
		repository.save(new Employee("Frodo", "Baggins", "ring bearer"));
	}

}
