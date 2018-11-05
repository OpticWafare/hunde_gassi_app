package control;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;

import model.DBManager;
import model.DateTime;
import model.Message;
import model.Notification;
import model.Notification_Body;
import model.Notification_Outer;
import model.ScheduledMessageHandlerTask;

/**
 * Servlet implementation class SendNotification
 */
@WebServlet("/SendNotification")
public class SendNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */
    public SendNotification() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	
		System.out.println("init send notification");
		
		// TODO woanders hin
		System.out.println("ScheduledMessageHandlerTask wird gestartet");
        Timer time = new Timer();
        time.schedule(new ScheduledMessageHandlerTask(), 0, TimeUnit.MINUTES.toMillis(1));
		//ResourceBundle.clearCache();   
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Neue Notification erhalten!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String username = (String) request.getParameter("username");
		System.out.println("Username: "+username);
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int date = Integer.parseInt(request.getParameter("date"));
		int hour = Integer.parseInt(request.getParameter("hour"));	
		int minute = Integer.parseInt(request.getParameter("minute"));
		String timeZoneID = request.getParameter("timezone");
		System.out.println("Timezone: "+timeZoneID);
		
		DBManager db = new DBManager();
		String fcmtoken = null;
		try {
			fcmtoken = db.getFcmtokenFromUsername(username);
		} catch (SQLException e) {
			response.getWriter().append("Nachricht konnte nicht gesendet werden. Grund: Fehler bei der Datenbank.");
			e.printStackTrace();
		}
		finally {
			try {
				db.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String title = "Martin";
		String body = "beweg die du FETTE SAU";
		
		if(fcmtoken != null) {
		
			System.out.println("FCMToken in der DB gefunden: "+fcmtoken);
			
			// Derzeitigen Timestamp holen
			//long currentTimestamp = new Date().getTime();
			// Timestamp der Nachricht holen
			TimeZone sendTimeZone = TimeZone.getTimeZone(timeZoneID);
			Calendar c = Calendar.getInstance();
			c.setTimeZone(sendTimeZone);
			c.set(year, month-1, date, hour, minute, 0);
			long sendTimestamp = c.getTime().getTime();
			
			c.setTime(new Date());
			c.setTimeZone(TimeZone.getDefault());
			long currentTimestamp = c.getTime().getTime();
			
			System.out.println("Current Timestamp: " + currentTimestamp);
			System.out.println("Timestamp der Nachricht: "+sendTimestamp);
			
			if(currentTimestamp >= sendTimestamp) {
				// Jetzt senden
				System.out.println("Message wird jetzt gesendet");
				
				try {
					NotificationSender.sendNotification(username, fcmtoken, title, body);
					
					response.getWriter().append("Nachricht wurde erfolgreich gesendet.");
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().append("Nachricht Senden fehlgeschlagen");
				}
			}
			else {
				// Später senden
				System.out.println("Message wird später gesendet");
				
				Message msg = new Message(username, fcmtoken, sendTimestamp, title, body);
				ScheduledMessageHandlerTask.addScheduledMessage(msg);
				
				response.getWriter().append("Nachricht wird um "+c.getTime()+" gesendet");
			}
		}
	}



}