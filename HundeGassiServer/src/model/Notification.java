package model;

/**
 * Für Android Push Notifications
 * Repräsentiert das Message Objekt (innerhalb von Notification_Outer)
 * Enthält den User FCM Token und die Notification selbst
 * @see Notification_Outer
 */
public class Notification {

  
    private String token;
    /** Inhalt der Notification */
    private Notification_Body notification;

    /**
     * Konstruktor
     * @param condition Abfrage an welche Geräte die Notification gesendet werden soll (Topics)
     * @param data Notification Inhalt
     */
    public Notification(String token, Notification_Body data)
    {
        this.token = token;
        this.notification = data;
    }
}
