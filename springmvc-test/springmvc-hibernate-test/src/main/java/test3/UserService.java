package test3;

import java.util.List;

public interface UserService {
	public void addSingleUser(User user);

	public List<User> getAllUser();

	public boolean deleteSingleUser(String id);

	public User getSingleUser(String id);

	public boolean updateSingleUser(User user);
}
