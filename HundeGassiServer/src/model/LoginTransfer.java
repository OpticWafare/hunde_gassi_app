package model;

/**
 * Ein Objekt aus dieser Klasse wird nach dem Login/Registrieren eines Nutzers an das Endger�t gesendet
 * 
 *  Wenn Fehler aufgetreten sind: Error String Variable ist bef�llt
 *  Wenn erfolgeich: User Objekt ist bef�llt
 *  
 * @author User
 *
 */
public class LoginTransfer {

	private String error;
	private User user;
		
	public LoginTransfer(User user) {
		super();
		this.user = user;
	}
	
	public LoginTransfer(String error) {
		super();
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
}