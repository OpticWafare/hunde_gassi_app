package model;

public class User {

	private int userid;
	private String username;
	private String password;
	private String email;
	private String fcmtoken;
	
	
	
	public User(int userid, String username, String password, String email, String fcmtoken) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fcmtoken = fcmtoken;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFcmtoken() {
		return fcmtoken;
	}

	public void setFcmtoken(String fcmtoken) {
		this.fcmtoken = fcmtoken;
	}
	
	
	
}
