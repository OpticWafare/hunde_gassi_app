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

	/**
	 * Speichert das mitgegebene Route-Objekt (mit dessen Points) in die Datenbank
	 * @param route
	 */
	public void saveRoute(Route route)
	{
		try {
			PreparedStatement pstm = getConnection().prepareStatement("Insert into route (startTime, endTime, userid) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			//pstm.setDate(1, route.getStartTime());
			//pstm.setDate(2, route.getEndTime());
			//pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis()/1000));
			
			pstm.setTimestamp(1, new Timestamp(route.getStartTime()), Calendar.getInstance());
			pstm.setTimestamp(2, new Timestamp(route.getEndTime()), Calendar.getInstance());
			
			pstm.setInt(3, route.getUserid());
			pstm.execute();
			
			ResultSet generatedKeys = pstm.getGeneratedKeys();
			if (generatedKeys.next()) {
            	route.setRouteid(generatedKeys.getInt(1));
            }
			
			for(int i = 0; i < route.getPoints().size(); i++)
			{
				Point point = route.getPoints().get(i);
				PreparedStatement pstm2 = getConnection().prepareStatement("Insert into point (longitude, latitude, routeid) values (?, ?, ?)");
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
	
	public List<Route> selectRoute(int userid)
	{
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm1 = null;
		ResultSet rs1 = null;
		Route route = null;
		Point point = null;
		ArrayList<Point> points = null;;
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			pstm = getConnection().prepareStatement("SELECT * from route where userid = ? ORDER BY routeid DESC LIMIT 15");
			pstm.setInt(1, userid);
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				route = new Route(rs.getInt(1), rs.getTimestamp(2).getTime(), rs.getTimestamp(3).getTime(), userid);
				
				points = new ArrayList<Point>();
				
				 pstm1 = getConnection().prepareStatement("SELECT * from point where routeid = ?");
				 pstm1.setInt(1, route.getRouteid());
				 rs1 = pstm1.executeQuery();
				 while(rs1.next())
				 {
					 point = new Point(rs1.getInt(1), rs1.getDouble(2), rs1.getDouble(3), rs1.getInt(4));
					 points.add(point);
				 }
				 route.setPoints(points);
				routes.add(route);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routes;
	}

	public Connection getConnection() throws SQLException {
		if(conn == null) {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/hunde_gassi_app" +
					"?user=root&password=&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Vienna"); 
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
