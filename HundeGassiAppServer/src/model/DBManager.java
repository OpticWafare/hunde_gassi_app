package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

	/**
	 * Legt den angegebenen user in der Datenbank an 
	 * @param user
	 * @throws SQLException 
	 */
	public void createUser(User user) throws SQLException {
		
		PreparedStatement pstm = getConnection().prepareStatement("INSERT INTO USER(username, password, email) VALUES (?, ?, ?)");
		
		pstm.setString(1, user.getUsername());
		pstm.setString(2, user.getPassword());
		pstm.setString(3, user.getEmail());
		
		pstm.execute();
	}
	
	public Connection getConnection() {
		return null;
	}
	
}
