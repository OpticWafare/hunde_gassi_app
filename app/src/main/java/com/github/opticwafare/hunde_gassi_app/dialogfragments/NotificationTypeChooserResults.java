package com.github.opticwafare.hunde_gassi_app.dialogfragments;

public class NotificationTypeChooserResults {

    private boolean sendViaEmail;
    private boolean sendViaPush;

    public NotificationTypeChooserResults(boolean sendViaEmail, boolean sendViaPush) {
        this.sendViaEmail = sendViaEmail;
        this.sendViaPush = sendViaPush;
    }

    public boolean isSendViaEmail() {
        return sendViaEmail;
    }

    public void setSendViaEmail(boolean sendViaEmail) {
        this.sendViaEmail = sendViaEmail;
    }

    public boolean isSendViaPush() {
        return sendViaPush;
    }

    public void setSendViaPush(boolean sendViaPush) {
        this.sendViaPush = sendViaPush;
    }

    @Override
    public String toString() {
        return "NotificationTypeChooserResults{" +
                "sendViaEmail=" + sendViaEmail +
                ", sendViaPush=" + sendViaPush +
                '}';
    }

    public String toUrlParameter() {
        return "&sendViaEmail="+sendViaEmail+"&sendViaPush="+sendViaPush;
    }
}
