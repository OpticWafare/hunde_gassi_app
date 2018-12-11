package com.github.opticwafare.hunde_gassi_app.elements;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.listener.FriendClickedListener;
import com.github.opticwafare.hunde_gassi_app.model.DateTime;
import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;

import java.util.Calendar;
import java.util.Date;

public class ContactElement extends UIElement {

    private User user;
    private Activity activity;
    private TextView textViewFriendName;

    public ContactElement(Activity activity, int xmlLayout, User user) {
        super(xmlLayout);
        this.activity = activity;
        this.user = user;
    }

    public ViewGroup show(LayoutInflater layoutInflater, ViewGroup container, int nr) {

        ViewGroup layout = super.show(layoutInflater, container);

        // Hintergrund setzen
        if((nr % 2) == 1) {
            layout.setBackgroundColor(Color.LTGRAY);
        }

        // Text setzen
        textViewFriendName = (TextView) layout.findViewById(R.id.textViewFriendName);
        if(user != null) {
            textViewFriendName.setText(user.getUsername());
        }
        else {
            textViewFriendName.setText("");
        }

        layout.setOnClickListener(new FriendClickedListener(user, activity));

        return layout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TextView getTextViewFriendName() {
        return textViewFriendName;
    }
}
