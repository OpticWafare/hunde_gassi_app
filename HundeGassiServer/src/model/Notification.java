package model;

/**
 * F�r Android Push Notifications
 * Repr�sentiert das Message Objekt (innerhalb von Notification_Outer)
 * Enth�lt den User FCM Token und die Notification selbst
 * @see Notification_Outer
 */
public class Notification {

  
    private String token;
    /** Inhalt der Notification */
    private Notification_Body notification;

    /**
     * Konstruktor
     * @param condition Abfrage an welche Ger�te die Notification gesendet werden soll (Topics)
     * @param data Notification Inhalt
     */
    public Notification(String token, Notification_Body data)
    {
        this.token = token;
        this.notification = data;
    }
}
