package com.zhaohe.study.springmvc.controller.dao.impl;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zhaohe.study.springmvc.controller.dao.UserDao;
import com.zhaohe.study.springmvc.model.User;

public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addSingleUser(User user) {
		System.out.println("sessionFactory");
		Session session = sessionFactory.getCurrentSession();
		System.out.println(session);
		session.save(user);
		//session.flush();
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

	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
*/
}
