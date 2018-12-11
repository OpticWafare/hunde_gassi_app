package com.github.opticwafare.hunde_gassi_app.tabs;

import android.content.DialogInterface;
import android.widget.Button;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserDismissed;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserFragment;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserResults;
import com.github.opticwafare.hunde_gassi_app.listener.NotificationButtonListener;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;
import com.github.opticwafare.hunde_gassi_app.model.DateTime;
import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;

public class NotificationTab extends SuperTab implements DateTimePickerChanged, NotificationTypeChooserDismissed {

    private Button notificationButton;

    private NotificationTypeChooserResults notificationTypeChooserResults = null;

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
        // TODO title & body variabel
        // TODO notificationTypeChooserResults
        SendNotificationTask notificationTask = new SendNotificationTask(chosenDateTime, User.getLoggedInUser().getUsername(), "HundeGassiApp", "Gehe mit deinem Hund Gassi!");
        notificationTask.setNotificationTypes(notificationTypeChooserResults);
        notificationTask.setActivity(getMainActivity());
        notificationTask.execute("");
    }

    @Override
    public void onDismiss(DialogInterface dialog, NotificationTypeChooserResults results) {
        this.notificationTypeChooserResults = results;
        System.out.println("NotificationTab hat das Resultat des NotificationTypeChooser erhalten: " + results);
    }
}
