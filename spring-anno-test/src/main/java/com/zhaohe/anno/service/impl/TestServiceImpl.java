package com.zhaohe.anno.service.impl;

import org.springframework.stereotype.Service;

import com.zhaohe.anno.service.TestService;

@Service(value="testService")
public class TestServiceImpl implements TestService{

	@Override
	public void show() {
		System.out.println("执行TestServiceImpl.show()");
	}

}
