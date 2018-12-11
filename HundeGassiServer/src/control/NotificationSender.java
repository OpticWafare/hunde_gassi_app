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
import java.util.Arrays;
import java.util.Scanner;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;

import model.Notification;
import model.Notification_Body;
import model.Notification_Outer;

/**
 * Sendet Notification an Smartphones
 * @author User
 *
 */
public class NotificationSender {

	/** Google-URL an die eine Notification gesendet wird (wird von dort aus an die betroffenen Geräte weiterverteilt) */
	private final static String API_URL = "https://fcm.googleapis.com/v1/projects/hunde-gassi-app/messages:send";
	
	public final static String[] SCOPES = new String[]
			{"https://www.googleapis.com/auth/firebase.messaging"};
	
	/**
	 * Sendet eine Notification an ein Smartphone
	 * @param username
	 * @param fcmtoken
	 * @param title
	 * @param message
	 * @throws Exception
	 */
	public static void sendNotification(String username, String fcmtoken, String title, String message) throws Exception {
		
		// Verbindung aufbauen
		HttpURLConnection con = null;
		con = getConnection();
		
		Notification_Body body = new Notification_Body(title, message);
	
		// Gson Objekt zur Umwandlung der Java Objekte in JSON
		Gson gson = new Gson();
		
		// Notification Objekte aufbauen
		Notification notification = new Notification(fcmtoken, body);
		Notification_Outer notificationOuter = new Notification_Outer(notification);
		
		// Gesamte Notification in JSON umwandeln
		String notificationGson = gson.toJson(notificationOuter);
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		// Notification über die Verbindung an den Server senden
		wr.write(notificationGson);
		System.out.println("Gesendetes JSON: " + notificationGson);
		wr.flush();
		wr.close();
		
		// Antwortcode empfangen
		int responseCode = con.getResponseCode();
		System.out.println("Android Antwortcode " + responseCode);
		// TODO response code auslesen und handlen
	
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
		System.out.println("Google Token: " + googleCredential.getAccessToken());
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
