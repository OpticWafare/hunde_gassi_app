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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;

import model.DBManager;
import model.Notification;
import model.Notification_Body;
import model.Notification_Outer;

/**
 * Servlet implementation class SendNotification
 */
@WebServlet("/SendNotification")
public class SendNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static String API_URL = "https://fcm.googleapis.com/v1/projects/hunde-gassi-app/messages:send";
	
	public final static String[] SCOPES = new String[]
			{"https://www.googleapis.com/auth/firebase.messaging"};
	
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

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String username = (String) request.getParameter("username");
		System.out.println("Username: "+username);
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
		
		if(fcmtoken != null) {
		
			// Verbindung aufbauen
			HttpURLConnection con = null;
			try {
				con = getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Notification_Body body = new Notification_Body("Test titel", "test body");
		
			// Gson Objekt zur Umwandlung der Java Objekte in JSON
			Gson gson = new Gson();
			
			String bodyString = "body test variable";
			
			// Notification Objekte aufbauen
			Notification notification = new Notification(fcmtoken, body);
			Notification_Outer notificationOuter = new Notification_Outer(notification);
			
			// Gesamte Notification in JSON umwandeln
			String notificationGson = gson.toJson(notificationOuter);
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			// Notification übber die Verbindung an den Server senden
			wr.write(notificationGson);
			System.out.println("Gesendetes JSON: " + notificationGson);
			wr.flush();
			wr.close();
			
			// Antwortcode empfangen
			int responseCode = con.getResponseCode();
			System.out.println("Android Antwortcode " + responseCode);
		
			// Eventuell Rückmeldungen ausgeben
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer googleResponse = new StringBuffer();
			// Rückmeldung pro Line auslesen
			while((inputLine = in.readLine()) != null)
			{
				googleResponse.append(inputLine);
			}
			in.close();
			// Verbindung schließen
			con.disconnect();
			
			System.out.println(googleResponse.toString());
			
			// TODO E-Mail senden
			
			response.getWriter().append("Nachricht wurde erfolgreich gesendet.");
		}
	}

	/**
	 * Gibt den aktuell gÃ¼ltigen Zugriffs-Token fÃ¼r die Kommunikation mit
	 * dem Google Messaging Server zurÃ¼ck
	 * @return Zugriffstoken
	 * @throws IOException
	 */
	private static String getAccessToken() throws IOException {
		// Google Credentials einstellen
		InputStream inputStream = new ByteArrayInputStream(getAuthFile().getBytes(StandardCharsets.UTF_8));
		GoogleCredential googleCredential = GoogleCredential
				.fromStream(inputStream)
				.createScoped(Arrays.asList(SCOPES));

		/*
		 * 		GoogleCredential googleCredential = GoogleCredential
				.fromStream(new FileInputStream(getAuthFile()))
				.createScoped(Arrays.asList(SCOPES));
		 */

		// Neuen Token holen (falls vorhanden)
		googleCredential.refreshToken();
		System.out.println("Token: " + googleCredential.getAccessToken());
		// Token zurückgeben
		return googleCredential.getAccessToken();
	}
	
	/**
	 * Baut eine neue Verbindung zum Google Messaging Server auf
	 * EnthÃ¤lt Header-Daten (Authorization, Verbindungsart, ...) 
	 * @return neue Verbindung zum Google Server
	 * @throws Exception
	 */
	private static HttpURLConnection getConnection() throws Exception {
		URL url = new URL(API_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);

		// Mit POST senden
		con.setRequestMethod("POST");
		// Authentifizierung mit Zugriffs-Token
		con.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		con.setRequestProperty("Content-Type", "application/json; UTF-8");
		
		return con;
	}
	
	/**
	 * @return Inhalt der Authentifizierungs-Datei (JSON)
	 */
	private static String getAuthFile() {
		// Authorization-Datei angeben
		InputStream in = SendNotification.class.getResourceAsStream("/model/authorization.json");
		String content = "";
		// Scanner fÃ¼r Datei erstellen
		Scanner scan = new Scanner(in, "UTF-8").useDelimiter("\\A");
		// Inhalt der Datei holen
		while(scan.hasNext() == true) {
			content += scan.next();
		}
		scan.close();
		// Inhalt zurückgeben
		return content;
	}

}