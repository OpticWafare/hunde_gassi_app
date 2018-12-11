package com.github.opticwafare.hunde_gassi_app.tabs;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import android.support.v4.app.Fragment;

public abstract class SuperTab {

    /** Der Titel den der Reiter dieses Tabs haben soll */
    private String tabTitle;
    /** ID der XML Datei */
    private int layoutXML;

    protected MainActivity mainActivity;

    public SuperTab(String tabTitle, int layoutXML) {
        this.tabTitle = tabTitle;
        this.layoutXML = layoutXML;
    }

    public abstract void init(MainActivity mainActivity);

    public String getTabTitle() {
        return tabTitle;
    }

    public int getLayoutXML() {
        return layoutXML;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
