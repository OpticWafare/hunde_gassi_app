package com.github.opticwafare.hunde_gassi_app;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;
import com.github.opticwafare.hunde_gassi_app.model.SendNotificationTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationButtonListener implements View.OnClickListener {

    private AppCompatActivity activity;

    public NotificationButtonListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        // Date Time Picker aufrufen
        DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment(getActivity());
        dateTimePickerFragment.addListener((DateTimePickerChanged) getActivity());
        dateTimePickerFragment.setContext(getActivity());
        dateTimePickerFragment.showDateTimePicker();
    }

    public AppCompatActivity getActivity() {
        return activity;
    }
}
