package com.github.opticwafare.hunde_gassi_app.model;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendNotificationTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        // TODO
        // TODO string params implementieren

        String targetURL = "http://10.0.2.2:8080/HundeGassiServer/SendNotification";
        String urlParameters = "username=testuser";
        HttpURLConnection connection = null;
        String serverResponse = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US"); // TODO

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            serverResponse = response.toString();
            rd.close();
            //return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return serverResponse;
    }

    @Override
    protected void onPostExecute(String serverResponse) {
        // TODO toast
        System.out.println("Server Response: "+serverResponse);
    }
}
