package model.dao.inerfaces;
import java.util.List;

import model.entity.User;

public interface UserDAO {

	public User getUserByEmailAndPassword(String login, String password);

	public List<User> selectAll();

	public User selectById(int id);

	public User selectByEmail(String email);

	public int update(User user);

	public boolean insert(User user);

	public boolean delete(User user);

}
