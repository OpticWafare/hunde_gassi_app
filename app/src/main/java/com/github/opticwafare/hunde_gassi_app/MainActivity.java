package com.github.opticwafare.hunde_gassi_app;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FixedTabsPagerAdapter pagerAdapter;

    private Boolean mLocationPermissionsGranted = false;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new FixedTabsPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new FCMTokenTaskCompleteListener());
    }

    @Override
    public String toString() {
        return super.toString()+"- MainActivity";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;

                    pagerAdapter.locationAccessGranted();
                }
            }
        }
    }

    public Boolean getmLocationPermissionsGranted() {
        return mLocationPermissionsGranted;
    }

    public void setmLocationPermissionsGranted(Boolean mLocationPermissionsGranted) {
        this.mLocationPermissionsGranted = mLocationPermissionsGranted;
    }
}
