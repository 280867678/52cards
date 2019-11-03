package model;

public class User {
	private String userName;
	private String nickName;
	private String password;
	private int money;
	public User(String userName, String nickName, String password) {
		super();
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.setMoney(3000);
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}
