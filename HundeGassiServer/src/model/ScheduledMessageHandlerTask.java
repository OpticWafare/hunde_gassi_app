package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import control.NotificationSender;

/**
 * TimerTask für das Senden von aufgeschobenen Nachrichten
 * @author User
 *
 */
public class ScheduledMessageHandlerTask extends TimerTask {

	/** Alle aufgeschobenen Nachrichten (werden später gesendet)
	 * TODO eventuell in Datenbank verlagern?
	 * */
	private static ArrayList<Message> scheduledMessages = new ArrayList<>();
	
	/**
	 * 
	 * @param message Nachricht die später gesendet werden soll
	 */
	public static void addScheduledMessage(Message message) {
		scheduledMessages.add(message);
		System.out.println("Neue Nachricht zu den scheduled Messages hinzugefügt."
		+"Es sind jetzt "+scheduledMessages.size()+" Nachrichten in der Warteschlange");
	}
	
	@Override
	/**
	 * Inhalt des Tasks
	 * Geht durch alle aufgeschobenen Nachrichten durch und schaut
	 * welche von denen jetzt gesendet werden sollen
	 */
	public void run() {

		System.out.println("------------------------------------");
		System.out.println("Neuer Durchlauf von ScheduledMessageHandlerTask");
		
		// Aktuellen Timestamp holen
		long currentTimestamp = new Date().getTime();
		System.out.println("Current Timestamp: "+currentTimestamp);
		
		Message message = null;
		// Rückwärts durch alle aufgeschobenen Nachrichten durchgehen
		for(int i = scheduledMessages.size()-1; i >= 0; i--) 
		{
			message = scheduledMessages.get(i);
			System.out.println("Timestamp der "+i+". nachricht: "+message.getSendTime());
			// Wenn die Sendezeit der Nachricht jetzt oder in der Vergangenheit ist -> Nachricht senden
			if(currentTimestamp >= message.getSendTime()) {
				try {
					NotificationSender.sendNotification(message.getUsername(), message.getFcmToken(), message.getTitle(), message.getBody());
					System.out.println("Nachricht wurde erfolgreich gesendet");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Nachricht aus den aufgeschobenen Nachrichten entfernen
				scheduledMessages.remove(message);
			}
		}
		System.out.println("ScheduledMessageHandlerTask ENDE");
		System.out.println("------------------------------------");
	}
}
