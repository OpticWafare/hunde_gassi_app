package com.github.opticwafare.hunde_gassi_app.servlettasks;

import android.app.Activity;
import android.widget.Toast;

import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserResults;
import com.github.opticwafare.hunde_gassi_app.model.DateTime;

public class SendNotificationTask extends SendToServletTask {

    private DateTime dateTime;
    private String username;
    private String messageTitle;
    private String messageBody;

    private NotificationTypeChooserResults notificationTypes;

    private Activity activity;

    public SendNotificationTask(DateTime dateTime, String username, String messageTitle, String messageBody) {
        setServletName("SendNotification");
        this.dateTime = dateTime;
        this.username = username;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;

        System.out.println("SendNotificationTask created: " + username + " " + dateTime + " " + messageTitle + " " + messageBody);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        String urlParameter = "username="+username+"&"+dateTime.toURLParameter()+"&title="+messageTitle+"&body="+messageBody;
        if(notificationTypes != null) {
            urlParameter += notificationTypes.toUrlParameter();
        }
        System.out.println("SendNotificationTask: onPreExecute; sending URL Params: " + urlParameter);
        setUrlParameters(urlParameter);
    }

    @Override
    protected void onPostExecute(final String serverResponse) {

        if(activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, serverResponse, Toast.LENGTH_SHORT).show();
                }
            });
        }
        System.out.println("Server Response from SendNotificationTask: "+serverResponse);
        System.out.println("Activity: " + activity);
    }

    // Getter & Setter

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public NotificationTypeChooserResults getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(NotificationTypeChooserResults notificationTypes) {
        this.notificationTypes = notificationTypes;
    }
}
