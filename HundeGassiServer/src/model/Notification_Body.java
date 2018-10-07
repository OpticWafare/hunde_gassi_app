package model;

/**
 * Für Android Push Notifications
 * ReprÃ¤sentiert die Daten, die in der
 * Notification angezeigt werden sollen
 * (Titel und Inhalt)
 *
 */
public class Notification_Body {

    /** Titeltext der Notification */
    private String title;
    /** Inhalt der Notification */
    private String body;

    /**
     * Konstruktor
     * @param title Titeltext der Notification
     * @param body Inhalt der Notification
     */
    public Notification_Body(String title, String body)
    {
        this.title = title;
        this.body = body;
    }
}
