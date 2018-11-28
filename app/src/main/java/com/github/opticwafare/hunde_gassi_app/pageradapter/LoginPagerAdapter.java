package com.github.opticwafare.hunde_gassi_app.pageradapter;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.tabs.LoginTab;
import com.github.opticwafare.hunde_gassi_app.tabs.RegisterTab;
import com.github.opticwafare.hunde_gassi_app.tabs.SettingsTab;

public class LoginPagerAdapter extends FixedTabsPagerAdapter {

    public LoginPagerAdapter(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    protected void init() {

        addTab(new SettingsTab());
        addTab(new LoginTab());
        addTab(new RegisterTab());
    }
}