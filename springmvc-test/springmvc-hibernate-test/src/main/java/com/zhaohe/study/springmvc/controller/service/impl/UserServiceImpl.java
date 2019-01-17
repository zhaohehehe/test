package com.zhaohe.study.springmvc.controller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaohe.study.springmvc.controller.dao.impl.UserDaoImpl;
import com.zhaohe.study.springmvc.controller.service.UserService;
import com.zhaohe.study.springmvc.model.User;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDaoImpl; 
	@Override
	public void addSingleUser(User user) {
		userDaoImpl.addSingleUser(user);
		System.out.println("userdaoimpl");
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteSingleUser(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getSingleUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateSingleUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	

}
