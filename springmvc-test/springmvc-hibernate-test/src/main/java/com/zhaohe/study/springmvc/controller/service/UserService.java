package com.zhaohe.study.springmvc.controller.service;

import java.util.List;

import com.zhaohe.study.springmvc.model.User;

public interface UserService {
	public void addSingleUser(User user);

	public List<User> getAllUser();

	public boolean deleteSingleUser(String id);

	public User getSingleUser(String id);

	public boolean updateSingleUser(User user);
}
