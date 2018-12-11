package com.github.opticwafare.hunde_gassi_app.tabs;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.elements.ContactElement;
import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.servlettasks.GetMyFriendsTask;

import java.util.ArrayList;

/**
 * Tab, welcher die Kontaktliste (Freundesliste) enthält
 */
public class ContactsTab extends SuperTab {

    private Button buttonNewContact;
    private ScrollView scrollViewContacts;
    private LinearLayout linearLayoutContacts;

    private User[] myFriends;
    private ArrayList<ContactElement> contactElements;

    public ContactsTab() {
        super("Kontakte", R.layout.activity_contacts);
    }

    @Override
    public void init(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        //
        //
        //
        // buttonNewContact = (Button) mainActivity.findViewById(R.id.buttonNewContact);
        //scrollViewContacts = (ScrollView) mainActivity.findViewById(R.id.scrollViewContacts);
        linearLayoutContacts = (LinearLayout) mainActivity.findViewById(R.id.linearLayoutContacts);

        contactElements = new ArrayList<>();

        GetMyFriendsTask getMyFriendsTask = new GetMyFriendsTask(this);
        getMyFriendsTask.execute("");
    }

    public void showFriends(User[] users) {

        myFriends = users;

        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        ContactElement contactElement = null;
        ViewGroup layout = null;
        for(int i = 0; i < myFriends.length; i++) {

            contactElement = new ContactElement(mainActivity, R.layout.element_contact, myFriends[i]);
            contactElements.add(contactElement);
            layout = contactElement.show(inflater, linearLayoutContacts, i);

            // GUI zum Container hinzufügen
            linearLayoutContacts.addView(layout);
        }
        // TODO keine Freunde meldung
    }
}
