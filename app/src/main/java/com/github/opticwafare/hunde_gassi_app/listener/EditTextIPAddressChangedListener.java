package com.github.opticwafare.hunde_gassi_app.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.github.opticwafare.hunde_gassi_app.servlettasks.SendToServletTask;

public class EditTextIPAddressChangedListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String ipAddress = s.toString();
        System.out.println("New IP Address: " + ipAddress);
        SendToServletTask.setServerIPAddress(ipAddress);
    }
}
