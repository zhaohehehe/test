package test6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDaoImpl;

	public void addSingleUser(User user) {
		userDaoImpl.addSingleUser(user);
	}
	
}
