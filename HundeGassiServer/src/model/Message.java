package model;

public class Message {

	private String username;
	private String fcmToken;
	private long sendTime;
	private String title;
	private String body;
	
	public Message(String username, String fcmToken, long sendTime, String title, String body) {
		super();
		this.username = username;
		this.fcmToken = fcmToken;
		this.sendTime = sendTime;
		this.title = title;
		this.body = body;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}	
}
