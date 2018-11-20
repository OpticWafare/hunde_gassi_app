package com.github.opticwafare.hunde_gassi_app.listener;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;

public class NotificationButtonListener implements View.OnClickListener {

    private AppCompatActivity activity;
    private DateTimePickerChanged listener;

    public NotificationButtonListener(AppCompatActivity activity, DateTimePickerChanged listener) {
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        // Date Time Picker aufrufen
        DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment(getActivity());
        dateTimePickerFragment.addListener(getListener());
        dateTimePickerFragment.setContext(getActivity());
        dateTimePickerFragment.showDateTimePicker();
    }

    public DateTimePickerChanged getListener() {
        return listener;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }
}
