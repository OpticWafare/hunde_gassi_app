package com.github.opticwafare.hunde_gassi_app.model;

/**
 * Für Android Push Notifications
 * Repräsentiert das Message Objekt (innerhalb von Notification_Outer)
 * Enthält das Thema und die Notification selbst
 * @see Notification_Outer
 */
public class Notification {

    /** Welche Bedingungen müssen erfüllt sein, damit
     * ein Gerätt diese Benachrichtigung erhällt? (welche Topics) */
    private String condition;
    /** Inhalt der Notification */
    private Notification_Body notification;

    /** Thema zu dem alle PostkastenÃ¤nderungen gesendet werden sollen */
    public final static String TOPIC_ALL = "postkasten";
    /** Thema zu dem Postkasten-Öffnungen gesendet werden sollen */
    public final static String TOPIC_OPEN = "postkasten_offen";
    /** Thema zu dem Postkasten-Schließungen gesendet werden sollen */
    public final static String TOPIC_CLOSE = "postkasten_geschlossen";

    /**
     * Konstruktor
     * @param condition Abfrage an welche Geräte die Notification gesendet werden soll (Topics)
     * @param data Notification Inhalt
     */
    public Notification(String condition, Notification_Body data)
    {
        this.condition = condition;
        this.notification = data;
    }
}
