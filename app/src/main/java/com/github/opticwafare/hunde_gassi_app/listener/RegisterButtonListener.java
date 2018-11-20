package com.github.opticwafare.hunde_gassi_app.listener;

import android.view.View;

import com.github.opticwafare.hunde_gassi_app.servlettasks.LoginTask;
import com.github.opticwafare.hunde_gassi_app.servlettasks.RegisterTask;
import com.github.opticwafare.hunde_gassi_app.tabs.LoginTab;
import com.github.opticwafare.hunde_gassi_app.tabs.RegisterTab;

public class RegisterButtonListener implements View.OnClickListener {

    private RegisterTab registerTab;

    public RegisterButtonListener(RegisterTab registerTab) {
        this.registerTab = registerTab;
    }

    @Override
    public void onClick(View v) {

        registerTab.getEditTextEmail().clearFocus();
        registerTab.getEditTextPassword().clearFocus();
        registerTab.getEditTextPassword2().clearFocus();
        registerTab.getEditTextUsername().clearFocus();

        RegisterTask registerTask = new RegisterTask(registerTab);
        registerTask.execute("");
    }
}
