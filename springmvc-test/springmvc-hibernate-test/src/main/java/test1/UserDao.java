package test1;

import java.util.List;

public interface UserDao {
	public void addSingleUser(User user);

	public List<User> getAllUser();

	public boolean deleteSingleUser(String id);

	public User getSingleUser(String id);

	public boolean updateSingleUser(User user);

}
