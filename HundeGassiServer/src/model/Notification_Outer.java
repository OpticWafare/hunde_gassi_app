package model;

/**
 * F�r Android Push Notifications
 * Repr�sentiert das gesamte Objekt, welches an den Server gesendet wird
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