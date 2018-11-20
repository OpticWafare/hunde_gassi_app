package com.github.opticwafare.hunde_gassi_app.listener;

import android.support.annotation.NonNull;

import com.github.opticwafare.hunde_gassi_app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;

/**
 * Dieser Listener wird aufgerufen wenn der Task zum Erhalten des FCM Tokens abgeschlossen ist
 */
public class FCMTokenTaskCompleteListener implements OnCompleteListener<InstanceIdResult> {

    @Override
    /**
     * Wenn der FCM Token verfügbar ist
     */
    public void onComplete(@NonNull Task<InstanceIdResult> task) {
        String fcmToken = task.getResult().getToken();
        System.out.println("FCM Token: " + fcmToken);

        if(User.getLoggedInUser() != null) {
            User.getLoggedInUser().setFcmtoken(fcmToken);
            System.out.println("FCM Token wurd am UserObjekt aktualisiert");
            // TODO servlet aufrufen und fcm token aktualisieren
        }
        else {
            System.out.println("FCM Token konnte nicht im UserObjekt gespeichert werden, da derzeit kein User eingeloggt ist. Wird als lonelyFcmToken gespeichert");
            User.setLonelyFcmToken(fcmToken);
        }
    }
}
