package test1;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDaoImpl; 
	@Override
	public void addSingleUser(User user) {
		// TODO Auto-generated method stub
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
	@Autowired
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	

}
