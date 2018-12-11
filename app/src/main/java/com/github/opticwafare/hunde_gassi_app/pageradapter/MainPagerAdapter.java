package com.github.opticwafare.hunde_gassi_app.pageradapter;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.tabs.ContactsTab;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.github.opticwafare.hunde_gassi_app.tabs.NotificationTab;
import com.github.opticwafare.hunde_gassi_app.tabs.SettingsTab;

public class MainPagerAdapter extends FixedTabsPagerAdapter {

    /** Der Tab der die Google Maps Karte beinhaltet  */
    private MapsTab mapsTab;

    public MainPagerAdapter(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    protected void init() {
        // Tabs hinzufügen
        addTab(new NotificationTab());
        mapsTab = new MapsTab();
        //addTab(new SettingsTab());
        addTab(mapsTab);
        addTab(new ContactsTab());
    }

    public void locationAccessGranted() {
        //initialize our map
        mapsTab.initMap();
    }

}
