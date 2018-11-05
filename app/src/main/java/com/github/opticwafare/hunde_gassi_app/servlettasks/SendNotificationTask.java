package com.github.opticwafare.hunde_gassi_app.servlettasks;

import com.github.opticwafare.hunde_gassi_app.model.DateTime;

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
