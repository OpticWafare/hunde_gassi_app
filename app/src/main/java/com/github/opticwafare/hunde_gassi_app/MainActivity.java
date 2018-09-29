package com.github.opticwafare.hunde_gassi_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    Button notificationButton = (Button) findViewById(R.id.button_notification);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationButton.setOnClickListener(new NotificationButtonListener());
    }



}
