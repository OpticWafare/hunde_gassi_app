package com.github.opticwafare.hunde_gassi_app;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


import com.github.opticwafare.hunde_gassi_app.model.Notification;
import com.github.opticwafare.hunde_gassi_app.model.Notification_Body;
import com.github.opticwafare.hunde_gassi_app.model.Notification_Outer;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.util.IOUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ServerNotification {

    public final static String API_URL = "https://fcm.googleapis.com/v1/projects/postkasten-4c221/messages:send";

    public final static String[] SCOPES = new String[]
            {"https://www.googleapis.com/auth/firebase.messaging"};

    /**
     * Notification an den Google Server senden
     * Dieser Sendet die Notification weiter an die benötigten Geräte
     * @param geoeffnet true = geöffnet ; false = geschlossen
     * @throws Exception
     */
    public static void sendNotification(boolean geoeffnet) throws Exception
    {
        // Verbindung aufbauen
        HttpURLConnection con = getConnection();

        Notification_Body body;
        // Themen, an die die Benachrichtigung gesendet werden soll
        ArrayList<String> topics = new ArrayList<String>();
        // Alle Benachrichtungen werden an dieses Topic gesendet:
        topics.add(Notification.TOPIC_ALL);
        // Notification Inhalt und Topics aufbauen (je nachdem ob Postkasten geöffnet oder geschlossen wurde)
        if(geoeffnet == true)
        {
            body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.BODY_OPEN);
            topics.add(Notification.TOPIC_OPEN);
        } else{
            body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.BODY_CLOSE);
            topics.add(Notification.TOPIC_CLOSE);
        }

        // Gson Objekt zur Umwandlung der Java Objekte in JSON
        Gson gson = new Gson();

        // Topics in Condition-String umwandeln
        String topicString = topicsToString(topics);
        // Notification Objekte aufbauen
        Notification notification = new Notification(topicString, body);
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
        StringBuffer response = new StringBuffer();
        // Rückmeldung pro Line auslesen
        while((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        in.close();
        // Verbindung schließen
        con.disconnect();

        // E-Mail senden
      //  MailUtility.sendNotificationMail(geoeffnet);
    }

    /**
     * Wandelt mehrere Topic-String in eine Condition um
     * @param topics Topics als Strings
     * @return Condition-String
     */
    private static String topicsToString(ArrayList<String> topics) {
        String str = "";
        for(int i = 0; i < topics.size(); i++) {
            if(i > 0) str += " || "; // Mehrere Topics mit ODER Verknüpfen
            str += "'"+topics.get(i)+"' in topics";
        }
        return str;
    }

    /**
     * Gibt den aktuell gÃ¼ltigen Zugriffs-Token fÃ¼r die Kommunikation mit
     * dem Google Messaging Server zurÃ¼ck
     * @return Zugriffstoken
     * @throws IOException
     */
    private static String getAccessToken() throws IOException {
        // Google Credentials einstellen
        /*InputStream inputStream = new ByteArrayInputStream(getAuthFile().getBytes(StandardCharsets.UTF_8));
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Arrays.asList(SCOPES));*/

        // Neuen Token holen (falls vorhanden)
        //googleCredential.refreshToken();
        //System.out.println("Token: " + googleCredential.getAccessToken());
        // Token zurückgeben
        return null;
        //return googleCredential.getAccessToken();
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
        InputStream in = ServerNotification.class.getResourceAsStream("/model/authorization.json");
        String content = "";
        // Scanner fÃ¼r Datei erstellen
        Scanner scan = new Scanner(in, "UTF-8").useDelimiter("\\A");
        // Inhalt der Datei holen
        while(scan.hasNext() == true) {
            content += scan.next();
        }
        // Inhalt zurückgeben
        return content;
    }
}