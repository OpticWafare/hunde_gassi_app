package com.github.opticwafare.hunde_gassi_app.listener;

import android.view.View;

import com.github.opticwafare.hunde_gassi_app.servlettasks.GetMyRoutesTask;
import com.github.opticwafare.hunde_gassi_app.servlettasks.LoginTask;
import com.github.opticwafare.hunde_gassi_app.tabs.LoginTab;

public class LoginButtonListener implements View.OnClickListener {

    private LoginTab loginTab;

    public LoginButtonListener(LoginTab loginTab) {
        this.loginTab = loginTab;
    }

    @Override
    public void onClick(View v) {

        loginTab.getEditTextUsername().clearFocus();
        loginTab.getEditTextPassword().clearFocus();

        LoginTask loginTask = new LoginTask(loginTab);
        loginTask.execute("");
    }
}
