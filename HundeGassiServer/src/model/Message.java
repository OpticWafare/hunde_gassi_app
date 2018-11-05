package model;

/**
 * Eine Nachricht die an ein Android Gerät gesendet werden soll
 * @author User
 *
 */
public class Message {

	private String username;
	/** FCMToken des Zielgeräts */
	private String fcmToken;
	/** Timestamp wann die Nachricht gesendet werden soll */
	private long sendTime;
	/** Titel der Nachricht */
	private String title;
	/** Inhalt der Nachricht */
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
