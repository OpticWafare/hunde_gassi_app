package com.github.opticwafare.hunde_gassi_app.elements;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public class UIElement {

    private LayoutInflater layoutInflater;
    private int xmlLayout;

    public UIElement(int xmlLayout) {
        this.xmlLayout = xmlLayout;
    }

    public ViewGroup show(LayoutInflater layoutInflater, ViewGroup container) {
        this.layoutInflater = layoutInflater;

        // XML in eine lauffähige GUI verwandeln
        ViewGroup layout = (ViewGroup) layoutInflater.inflate(xmlLayout, container, false);
        return layout;
    }

    public int getXmlLayout() {
        return xmlLayout;
    }

    public void setXmlLayout(int xmlLayout) {
        this.xmlLayout = xmlLayout;
    }
}
