package com.github.opticwafare.hunde_gassi_app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerChanged;
import com.github.opticwafare.hunde_gassi_app.dialogfragments.DateTimePickerFragment;
import com.github.opticwafare.hunde_gassi_app.model.DateTime;
import com.github.opticwafare.hunde_gassi_app.model.SendNotificationTask;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity implements DateTimePickerChanged {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new FCMTokenTaskCompleteListener());
    }

    Button notificationButton;

    public void startSendNotificationGUI() {

        notificationButton = (Button) findViewById(R.id.button_notification);
        notificationButton.setOnClickListener(new NotificationButtonListener(this));
    }

    @Override
    public String toString() {
        return super.toString()+"- MainActivity";
    }

    @Override
    public void dateTimeSet(DateTimePickerFragment view, DateTime chosenDateTime) {

        System.out.println(chosenDateTime);
        SendNotificationTask notificationTask = new SendNotificationTask();
        notificationTask.setDateTime(chosenDateTime);
        notificationTask.execute("");
        // TODO
    }
}
