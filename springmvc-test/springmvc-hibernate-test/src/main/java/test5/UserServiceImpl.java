package test5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDaoImpl;

	public String getNameOfSingleUser(int id) {
		return userDaoImpl.getNameOfSingleUser(id);
	} 
	
}
