package com.github.opticwafare.hunde_gassi_app.listener;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserDismissed;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserFragment;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.NotificationTypeChooserResults;

public class NotificationButtonListener implements View.OnClickListener, NotificationTypeChooserDismissed {

    private AppCompatActivity activity;
    private DateTimePickerChanged listener;

    public NotificationButtonListener(AppCompatActivity activity, DateTimePickerChanged listener) {
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        // Auswahldialog aufrufen: E-Mail oder Push-Notification?
        NotificationTypeChooserFragment notificationTypeChooserFragment = new NotificationTypeChooserFragment();
        // Listener hinzufügen (sich selbst und den anderen Listener)
        if(listener instanceof NotificationTypeChooserDismissed) {
            notificationTypeChooserFragment.addDismissListener((NotificationTypeChooserDismissed) listener);
        }
        notificationTypeChooserFragment.addDismissListener(this);
        // Auswahldialog anzeigen
        notificationTypeChooserFragment.show(getActivity().getSupportFragmentManager(), "notification type picker");
    }

    public DateTimePickerChanged getListener() {
        return listener;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Wenn Auswahldialog zwischen E-Mail und Push-Notification erledigt ist
     */
    @Override
    public void onDismiss(DialogInterface dialog, NotificationTypeChooserResults results) {

        // Date Time Picker aufrufen
        System.out.println("NotificationTypeChooser fertig, jetzt DateTimePicker aufrufen");
        DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment(getActivity());
        dateTimePickerFragment.addListener(getListener());
        dateTimePickerFragment.setContext(getActivity());
        dateTimePickerFragment.showDateTimePicker();
    }
}
