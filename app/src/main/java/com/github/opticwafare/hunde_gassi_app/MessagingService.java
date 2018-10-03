package com.github.opticwafare.hunde_gassi_app;

import com.github.opticwafare.hunde_gassi_app.model.DBManager;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        System.out.println("new token: "+s);
        DBManager dbManager = new DBManager();
        dbManager.updateFCMToken(s);
    }
}
