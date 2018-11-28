package com.github.opticwafare.hunde_gassi_app.servlettasks;

import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.tabs.ContactsTab;
import com.google.gson.Gson;

/**
 * Holt alle Freunde des derzeitig angemeldeten Users
 */
public class GetMyFriendsTask extends SendToServletTask {

    private ContactsTab contactsTab;

    public GetMyFriendsTask(ContactsTab contactsTab) {

        this.contactsTab = contactsTab;
        setServletName("GetFriends");
        setUrlParameters("userId="+ User.getLoggedInUser().getUserid());
    }

    @Override
    protected void onPostExecute(String s) {

        Gson gson = new Gson();
        System.out.println("onPostExecute of GetMyFriendsTask: " + s);
        User[] users = gson.fromJson(s, User[].class);

        contactsTab.showFriends(users);
    }
}
