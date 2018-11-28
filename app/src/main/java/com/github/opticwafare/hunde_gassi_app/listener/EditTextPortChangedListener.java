package com.github.opticwafare.hunde_gassi_app.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.github.opticwafare.hunde_gassi_app.servlettasks.SendToServletTask;

public class EditTextPortChangedListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String port = s.toString();
        System.out.println("New Port: " + port);
        SendToServletTask.setServerPort(Integer.parseInt(port));
    }
}
