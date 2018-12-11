package com.github.opticwafare.hunde_gassi_app.listener;

import android.app.Activity;
import android.view.View;

import com.github.opticwafare.hunde_gassi_app.model.DateTime;
import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;

import java.util.Calendar;

public class FriendClickedListener implements View.OnClickListener {

    private User user;
    private Activity activity;

    public FriendClickedListener(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        System.out.println("Clicked on contact: " + user.getUsername());
        Calendar c = Calendar.getInstance();
        DateTime dateTime = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        SendNotificationTask sendNotificationTask = new SendNotificationTask(dateTime, user.getUsername(), "Von: "+ User.getLoggedInUser().getUsername(), "Bitte mit meinem Hund Gassi gehen!");
        sendNotificationTask.setActivity(activity);
        sendNotificationTask.execute("");
    }

    public User getUser() {
        return user;
    }

    public Activity getActivity() {
        return activity;
    }
}
