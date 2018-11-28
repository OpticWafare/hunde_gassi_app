package com.github.opticwafare.hunde_gassi_app.tabs;

import android.widget.EditText;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.listener.EditTextIPAddressChangedListener;
import com.github.opticwafare.hunde_gassi_app.listener.EditTextPortChangedListener;
import com.github.opticwafare.hunde_gassi_app.listener.EditTextServerNameChangedListener;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendToServletTask;

public class SettingsTab extends SuperTab {

    private EditText editTextIP;
    private EditText editTextPort;
    private EditText editTextServerName;

    public SettingsTab() {
        super("Einstellungen", R.layout.activity_settings);
    }

    @Override
    public void init(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        editTextIP = (EditText) mainActivity.findViewById(R.id.editTextIP);
        editTextPort = (EditText) mainActivity.findViewById(R.id.editTextPort);
        editTextServerName = (EditText) mainActivity.findViewById(R.id.editTextServerName);

        editTextIP.setText(SendToServletTask.getServerIPAddress());
        editTextPort.setText(SendToServletTask.getServerPort()+"");
        editTextServerName.setText(SendToServletTask.getServerName());

        editTextIP.addTextChangedListener(new EditTextIPAddressChangedListener());
        editTextPort.addTextChangedListener(new EditTextPortChangedListener());
        editTextServerName.addTextChangedListener(new EditTextServerNameChangedListener());
    }
}
