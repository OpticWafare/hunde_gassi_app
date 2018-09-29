package com.github.opticwafare.hunde_gassi_app.model;

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
    /** Standard Titel fÃ¼r eine Notification */
    public final static String DEFAULT_TITLE = "Postkastenänderung";
    /** Notification Text wenn Postkasten geÃ¶ffnet wurde */
    public final static String BODY_OPEN = "Postkasten wurde geöffnet!";
    /** Notification Text wenn Postkasten geschlossen wurde */
    public final static String BODY_CLOSE = "Postkasten wurde geschlossen!";

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
