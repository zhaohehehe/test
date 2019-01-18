package com.workspace.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workspace.spring.service.HelloWorldService;
@Controller
public class HelloSpringMvcController {

	@Autowired
	private HelloWorldService helloWorldService;
	
	@RequestMapping("/springmvc.html")
	public String getNewUserName(@RequestParam(value = "userName",required = false,defaultValue = "World") String  newUserName,HttpServletRequest request){
		String newName=helloWorldService.getNewUserName(newUserName);
		request.setAttribute("newUserName", newName);
		//����helloworld.jsp
		return "springmvc";
	}
}
