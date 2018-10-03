package com.github.opticwafare.hunde_gassi_app;

import android.support.annotation.NonNull;

import com.github.opticwafare.hunde_gassi_app.model.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;

public class FCMTokenTaskCompleteListener implements OnCompleteListener<InstanceIdResult> {
    @Override
    public void onComplete(@NonNull Task<InstanceIdResult> task) {
        System.out.println("FCM Token: " + task.getResult().getToken());
        DBManager dbManager = new DBManager();
        dbManager.updateFCMToken(task.getResult().getToken());
    }
}
