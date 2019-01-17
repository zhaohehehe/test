package com.zhaohe.study.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhaohe.study.springmvc.controller.service.impl.UserServiceImpl;
import com.zhaohe.study.springmvc.model.User;

@Controller
@RequestMapping("/user/*")//访问地址为：http://localhost:8080/spring-04/user/toAddSingleUser
public class UserCtrl {
	@Autowired
	private UserServiceImpl userService; 
	@RequestMapping("/toAddSingleUser")
	public String toAddSingleUser(){
		return "addSingleUser";
	}
	@RequestMapping("/addSingleUser")
	public String addSingleUser(){
		User user=new User();
		user.setPassword("123");user.setUsername("123");
		userService.addSingleUser(user);
		//return "redirect:/user/getAllUser";
		return "addSingleUser";
	}
}