package com.github.opticwafare.hunde_gassi_app;

import android.os.Message;
import android.view.View;

import com.github.opticwafare.hunde_gassi_app.model.SendNotificationTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        new SendNotificationTask().execute("");


    }
}
