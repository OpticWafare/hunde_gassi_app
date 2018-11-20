package com.github.opticwafare.hunde_gassi_app.pageradapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.github.opticwafare.hunde_gassi_app.tabs.NotificationTab;
import com.github.opticwafare.hunde_gassi_app.tabs.SuperTab;

import java.util.ArrayList;

/**
 * Handelt das Swipen zwischen den einzelnen Tabs
 *
 * Jeder Tab hat seine eigene Klasse, die von der Klasse SuberTab erbt
 */
public abstract class FixedTabsPagerAdapter extends PagerAdapter {

    private MainActivity mainActivity;

    /** Alle Tabs */
    private ArrayList<SuperTab> tabs = new ArrayList<>();

    public FixedTabsPagerAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        init();
    }

    protected abstract void init();

    @Override
    /** Gibt die Anzahl der Tabs zurück */
    public int getCount() {
        return tabs.size();
    }

    @NonNull
    @Override
    /**
     * Neuen Tab anzeigen
     */
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        // LayoutInflater erstellen. Wird verwendet um ein XML-Dokument in eine lauffähige GUI zu verwandeln
        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        // Der Tab der jetzt angezeigt werden soll
        SuperTab currentTab = tabs.get(position);

        // XML in eine lauffähige GUI verwandeln
        ViewGroup layout = (ViewGroup) inflater.inflate(currentTab.getLayoutXML(), container, false);
        // GUI zum Container hinzufügen
        container.addView(layout);
        // Init Methode des Tabs ausführen
        currentTab.init(mainActivity);
        // GUI zurückgeben
        return layout;

        // Alte version:
        /*switch (position) {
            case 0:


                //Intent myIntent = new Intent(mContext, SendNotification.class);
                //mContext.startActivity(myIntent);

                layout = (ViewGroup) inflater.inflate(R.layout.activity_sendnotification, container, false);

                //SendNotification sn = new SendNotification();
                //sn.setContentView(layout);
                //sn.init();
                container.addView(layout);
                mContext.startSendNotificationGUI();
                return layout;


                //container.addView(sn.getViewGroup());
                //return sn.getViewGroup();
            case 1:
                layout = (ViewGroup) inflater.inflate(R.layout.activity_maps, container, false);
                container.addView(layout);
                mContext.startMapsGUI();
                return layout;

            default: return null;
        }*/
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTabTitle();
    }

    public ArrayList<SuperTab> getTabs() {
        return tabs;
    }

    public void addTab(SuperTab tab) {
        this.tabs.add(tab);
    }
}
