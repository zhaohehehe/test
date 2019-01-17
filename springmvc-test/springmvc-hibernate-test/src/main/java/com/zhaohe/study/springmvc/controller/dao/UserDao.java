package com.zhaohe.study.springmvc.controller.dao;

import java.util.List;

import com.zhaohe.study.springmvc.model.User;

public interface UserDao {
	public void addSingleUser(User user);

	public List<User> getAllUser();

	public boolean deleteSingleUser(String id);

	public User getSingleUser(String id);

	public boolean updateSingleUser(User user);

}
