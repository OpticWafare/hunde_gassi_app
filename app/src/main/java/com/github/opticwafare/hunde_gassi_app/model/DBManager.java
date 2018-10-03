package com.github.opticwafare.hunde_gassi_app.model;

public class DBManager {

    private static String fcmToken;

    public void updateFCMToken(String newToken) {
        fcmToken = newToken;
        // TODO update in datenbank
    }

    public static String getFcmToken() {
        return fcmToken;
    }
}
