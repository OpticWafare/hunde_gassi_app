package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBManager {

	private Connection conn;

	/**
	 * Legt den angegebenen user in der Datenbank an
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void createUser(User user) throws SQLException {

		PreparedStatement pstm = getConnection()
				.prepareStatement("INSERT INTO USER(username, password, email, fcmtoken) VALUES (?, ?, ?, ?)");

		pstm.setString(1, user.getUsername());
		pstm.setString(2, user.getPassword());
		pstm.setString(3, user.getEmail());
		pstm.setString(4, user.getFcmtoken());

		pstm.execute();
	}

	public String getFcmtokenFromUsername(String username) throws SQLException {
		PreparedStatement pstm = getConnection().prepareStatement("SELECT fcmtoken from User where username = ?");
		pstm.setString(1, username);
		ResultSet rs = pstm.executeQuery();
		rs.next();

		return rs.getString(1);
	}

	/**
	 * Speichert das mitgegebene Route-Objekt (mit dessen Points) in die Datenbank
	 * 
	 * @param route
	 */
	public void saveRoute(Route route) {
		try {
			PreparedStatement pstm = getConnection().prepareStatement(
					"Insert into route (startTime, endTime, userid) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			// pstm.setDate(1, route.getStartTime());
			// pstm.setDate(2, route.getEndTime());
			// pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis()/1000));

			pstm.setTimestamp(1, new Timestamp(route.getStartTime()), Calendar.getInstance());
			pstm.setTimestamp(2, new Timestamp(route.getEndTime()), Calendar.getInstance());

			pstm.setInt(3, route.getUserid());
			pstm.execute();

			ResultSet generatedKeys = pstm.getGeneratedKeys();
			if (generatedKeys.next()) {
				route.setRouteid(generatedKeys.getInt(1));
			}

			for (int i = 0; i < route.getPoints().size(); i++) {
				Point point = route.getPoints().get(i);
				PreparedStatement pstm2 = getConnection()
						.prepareStatement("Insert into point (longitude, latitude, routeid) values (?, ?, ?)");
				pstm2.setDouble(1, point.getLongitude());
				pstm2.setDouble(2, point.getLatitude());
				pstm2.setInt(3, route.getRouteid());
				pstm2.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Route> selectRoute(int userid) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm1 = null;
		ResultSet rs1 = null;
		Route route = null;
		Point point = null;
		ArrayList<Point> points = null;
		;
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			pstm = getConnection()
					.prepareStatement("SELECT * from route where userid = ? ORDER BY routeid DESC LIMIT 15");
			pstm.setInt(1, userid);
			rs = pstm.executeQuery();

			while (rs.next()) {
				route = new Route(rs.getInt(1), rs.getTimestamp(2).getTime(), rs.getTimestamp(3).getTime(), userid);

				points = new ArrayList<Point>();

				pstm1 = getConnection().prepareStatement("SELECT * from point where routeid = ?");
				pstm1.setInt(1, route.getRouteid());
				rs1 = pstm1.executeQuery();
				while (rs1.next()) {
					point = new Point(rs1.getInt(1), rs1.getDouble(2), rs1.getDouble(3), rs1.getInt(4));
					points.add(point);
				}
				route.setPoints(points);
				routes.add(route);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routes;
	}

	public User getUserByEmail(String email) throws SQLException {

		System.out.println("getting user by email " + email);
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User ret = null;
		try {
			pstm = getConnection().prepareStatement("SELECT * FROM user WHERE email LIKE ?");
			pstm.setString(1, email);

			rs = pstm.executeQuery();
			System.out.println("result set: " + rs);
			ret = resultSetToUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return ret;
	}

	public User getUserByUsername(String username) throws SQLException {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		User ret = null;
		try {
			pstm = getConnection().prepareStatement("SELECT * FROM user WHERE username = ?");
			pstm.setString(1, username);

			rs = pstm.executeQuery();
			ret = resultSetToUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return ret;
	}

	public User loginUserEmail(String email, String password) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User ret = null;
		try {
			pstm = getConnection().prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
			pstm.setString(1, email);
			pstm.setString(2, password);
			rs = pstm.executeQuery();
			ret = resultSetToUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return ret;
	}

	public User loginUserUsername(String username, String password) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User ret = null;
		try {
			pstm = getConnection().prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
			pstm.setString(1, username);
			pstm.setString(2, password);
			rs = pstm.executeQuery();
			ret = resultSetToUser(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return ret;
	}

	/**
	 * Wandelt das angegebene ResultSet in ein User Objekt um.
	 * 
	 * Wenn Änderungen an der User Tabelle in der Datenbank vorgenommen wurden,
	 * müssen diese nur in dieser Methode und in der User Klasse angepasst werden.
	 * 
	 * @param rs
	 * @return
	 */
	public User resultSetToUser(ResultSet rs) {
		User user = null;
		boolean empty;
		try {
			empty = !rs.next();
			if (empty) {
				return null;
			}

			int userid = rs.getInt(1);
			String username = rs.getString(2);
			String password2 = rs.getString(3);
			String email2 = rs.getString(4);
			String fcmtoken = rs.getString(5);

			user = new User(userid, username, email2, password2, fcmtoken);
			System.out.println("Result set to user user: " + user);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<User> getFriendsFromUser(int userid) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstm = null;
		User user = null;
		List<User> users = new ArrayList<User>();
		try {
			pstm = getConnection().prepareStatement(
					"Select u.userid, u.username, u.email, u.fcmtoken from friend f JOIN user u on(f.friendid = u.userid) where f.userid = ?");
			pstm.setInt(1, userid);
			rs = pstm.executeQuery();
			while (rs.next() != false) {
				int friendid = rs.getInt(1);
				String username = rs.getString(2);
				String email = rs.getString(3);
				String fcmtoken = rs.getString(4);

				user = new User(friendid, username, "", email, fcmtoken);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return users;
	}
	
	

	public List<User> getAllUsers() throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<User> user = new ArrayList<User>();
		try {
			pstm = getConnection().prepareStatement("Select * from user");
			rs = pstm.executeQuery();
			User user1 = null;
			while (rs.next()) {
				user1 = new User(rs.getInt(1), rs.getString(2), "", rs.getString(4), rs.getString(5));
				user.add(user1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			releaseConnection();
		}

		return user;
	}

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/hunde_gassi_app"
					+ "?user=root&password=&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Vienna");
		}
		return conn;
	}

	public void releaseConnection() throws SQLException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}
}
