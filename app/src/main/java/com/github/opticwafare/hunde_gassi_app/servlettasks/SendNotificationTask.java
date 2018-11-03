package com.github.opticwafare.hunde_gassi_app.model;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendNotificationTask extends SendToServletTask {

    private DateTime dateTime;

    public SendNotificationTask() {
        setServletName("SendNotification");
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        // TODO
        setUrlParameters("username=testuser&"+dateTime.toURLParameter());
    }

    @Override
    protected void onPostExecute(String serverResponse) {
        // TODO toast
        System.out.println("Server Response: "+serverResponse);
    }
}
