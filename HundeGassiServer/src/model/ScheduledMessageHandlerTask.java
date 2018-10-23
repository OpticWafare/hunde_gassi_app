package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import control.NotificationSender;

public class ScheduledMessageHandlerTask extends TimerTask {

	private static ArrayList<Message> scheduledMessages = new ArrayList<>();
	
	public static void addScheduledMessage(Message message) {
		scheduledMessages.add(message);
		System.out.println("Neue Nachricht zu den scheduled Messages hinzugefügt."
		+"Es sind jetzt "+scheduledMessages.size()+" Nachrichten in der Warteschlange");
	}
	
	@Override
	public void run() {

		System.out.println("------------------------------------");
		System.out.println("Neuer Durchlauf von ScheduledMessageHandlerTask");
		long currentTimestamp = new Date().getTime();
		System.out.println("Current Timestamp: "+currentTimestamp);
		Message message = null;
		for(int i = scheduledMessages.size()-1; i >= 0; i--) 
		{
			message = scheduledMessages.get(i);
			System.out.println("Timestamp der "+i+". nachricht: "+message.getSendTime());
			if(currentTimestamp >= message.getSendTime()) {
				try {
					NotificationSender.sendNotification(message.getUsername(), message.getFcmToken(), message.getTitle(), message.getBody());
					System.out.println("Nachricht wurde erfolgreich gesendet");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				scheduledMessages.remove(message);
			}
		}
		System.out.println("ScheduledMessageHandlerTask ENDE");
		System.out.println("------------------------------------");
	}

}
