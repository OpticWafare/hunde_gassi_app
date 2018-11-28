package com.github.opticwafare.hunde_gassi_app.servlettasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendToServletTask extends AsyncTask<String, Void, String> {
    private String servletName;
    private String urlParameters;

    private static String serverIPAddress = "192.168.1.125";
    private static int serverPort = 8080;
    private static String serverName = "HundeGassiServer";

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        String serverResponse = null;

        String targetLocation = "http://"+serverIPAddress+":"+serverPort+"/"+serverName+"/";
        String targetURL = targetLocation + servletName;
        System.out.println("trying to connect to " + targetURL);
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

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(String urlParameters) {
        this.urlParameters = urlParameters;
    }

    public static String getServerIPAddress() {
        return serverIPAddress;
    }

    public static void setServerIPAddress(String serverIPAddress) {
        SendToServletTask.serverIPAddress = serverIPAddress;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        SendToServletTask.serverPort = serverPort;
    }

    public static String getServerName() {
        return serverName;
    }

    public static void setServerName(String serverName) {
        SendToServletTask.serverName = serverName;
    }
}
