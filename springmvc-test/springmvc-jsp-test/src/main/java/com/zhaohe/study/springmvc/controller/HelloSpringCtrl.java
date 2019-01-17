package com.zhaohe.study.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloSpringCtrl {
	@RequestMapping("/helloSpring")
	public String helloSpringMvc(){
		System.out.println("hello springmvc!");
		return "hello_v";
	}
}
