package dao;

import model.User;

public interface UserDao {
	User checkLogin(String userName, String password);
	boolean register(String userName, String nickName, String password);
}
