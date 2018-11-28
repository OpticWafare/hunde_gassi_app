package com.github.opticwafare.hunde_gassi_app.elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.model.User;

public class ContactElement extends UIElement {

    private User user;

    private TextView textViewFriendName;

    public ContactElement(int xmlLayout, User user) {
        super(xmlLayout);
        this.user = user;
    }

    public ViewGroup show(LayoutInflater layoutInflater, ViewGroup container) {

        ViewGroup layout = super.show(layoutInflater, container);

        textViewFriendName = (TextView) layout.findViewById(R.id.textViewFriendName);
        if(user != null) {
            textViewFriendName.setText(user.getUsername());
        }
        else {
            textViewFriendName.setText("");
        }

        return layout;
    }
}
