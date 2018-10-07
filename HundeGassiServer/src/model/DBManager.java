package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	private Connection conn;

	/**
	 * Legt den angegebenen user in der Datenbank an 
	 * @param user
	 * @throws SQLException 
	 */
	public void createUser(User user) throws SQLException {

		PreparedStatement pstm = getConnection().prepareStatement("INSERT INTO USER(username, password, email, fcmtoken) VALUES (?, ?, ?, ?)");

		pstm.setString(1, user.getUsername());
		pstm.setString(2, user.getPassword());
		pstm.setString(3, user.getEmail());
		pstm.setString(4, user.getFcmtoken());

		pstm.execute();
	}

	public String getFcmtokenFromUsername(String username) throws SQLException
	{
		PreparedStatement pstm = getConnection().prepareStatement("SELECT fcmtoken from User where username = ?");
		pstm.setString(1, username);
		ResultSet rs = pstm.executeQuery();
		rs.next();

		return rs.getString(1);
	}


	public Connection getConnection() throws SQLException {
		if(conn == null) {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/hunde_gassi_app" +
					"?user=root&password=&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Atlantic/Canary"); 
		}
		return conn;
	}

	public void closeConnection() throws SQLException {
		if(conn != null) {
			conn.close();
			conn = null;
		}
	}

	public static void main(String[] args) {
		DBManager db = new DBManager();
		try {
			db.getConnection();
			System.out.println(db.getFcmtokenFromUsername("testuser"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
