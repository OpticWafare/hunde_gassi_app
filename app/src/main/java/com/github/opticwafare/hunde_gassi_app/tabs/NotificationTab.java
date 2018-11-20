package com.github.opticwafare.hunde_gassi_app.tabs;

import android.widget.Button;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.listener.NotificationButtonListener;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;
import com.github.opticwafare.hunde_gassi_app.model.DateTime;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;

public class NotificationTab extends SuperTab implements DateTimePickerChanged {

    Button notificationButton;

    public NotificationTab() {
        super("Notification", R.layout.activity_sendnotification);
    }

    @Override
    public void init(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        notificationButton = (Button) mainActivity.findViewById(R.id.button_notification);
        notificationButton.setOnClickListener(new NotificationButtonListener(mainActivity, this));
    }

    @Override
    public void dateTimeSet(DateTimePickerFragment view, DateTime chosenDateTime) {

        System.out.println(chosenDateTime);
        SendNotificationTask notificationTask = new SendNotificationTask();
        notificationTask.setDateTime(chosenDateTime);
        notificationTask.execute("");
        // TODO
    }
}
