package com.github.opticwafare.hunde_gassi_app;

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

public class FixedTabsPagerAdapter extends PagerAdapter {

    private MainActivity mContext;

    public FixedTabsPagerAdapter(MainActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        switch (position) {
            case 0:
                LayoutInflater inflater = LayoutInflater.from(mContext);

                //Intent myIntent = new Intent(mContext, SendNotification.class);
                //mContext.startActivity(myIntent);

                ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_sendnotification, container, false);

                /*SendNotification sn = new SendNotification();
                sn.setContentView(layout);
                sn.init();*/
                container.addView(layout);
                mContext.startSendNotificationGUI();
                return layout;


                //container.addView(sn.getViewGroup());
                //return sn.getViewGroup();*/
            case 1: return null;
            default: return null;
        }
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
        return "test page";
    }
}
