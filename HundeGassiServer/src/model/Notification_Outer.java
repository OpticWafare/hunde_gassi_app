package model;

/**
 * Für Android Push Notifications
 * Repräsentiert das gesamte Objekt, welches an den Server gesendet wird
 *
 */
public class Notification_Outer {

    /** Nachricht selbst */
    Notification message;

    /**
     * Konstruktor
     * @param message Notification selbst
     */
    public Notification_Outer(Notification message) {
        this.message = message;
    }
}