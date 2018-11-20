package com.github.opticwafare.hunde_gassi_app.pageradapter;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.github.opticwafare.hunde_gassi_app.tabs.NotificationTab;

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
        addTab(mapsTab);
        // TODO mehrere tabs
    }

    public void locationAccessGranted() {
        //initialize our map
        mapsTab.initMap();
    }

}
