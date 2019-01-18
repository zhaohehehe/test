package com.workspace.spring.service.impl;

import org.springframework.stereotype.Service;

import com.workspace.spring.service.HelloWorldService;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{
	public String getNewUserName(String newUserName) {
		// TODO Auto-generated method stub
		return "Hello"+newUserName+"!";
	}

}
