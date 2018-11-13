package com.github.opticwafare.hunde_gassi_app.servlettasks;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GetMyRoutesTask extends SendToServletTask {

    private MapsTab mapsTab;

    public GetMyRoutesTask() {
        setServletName("GetRoutes");
        setUrlParameters("userid=1");
    }

    @Override
    protected void onPostExecute(String serverResponse) {
        // TODO toast
        System.out.println("Server Response from GetMyRoutesTask: "+serverResponse);

        Gson gson = new Gson();
        Route[] routen = gson.fromJson(serverResponse, Route[].class);

        List<String> list = new ArrayList<String>();
        for(int i = 0; i < routen.length; i++) {
            list.add(routen[i].toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getMapsTab().getMainActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getMapsTab().getSpinner().setAdapter(dataAdapter);

        mapsTab.setWalkedRoutes(routen);
    }

    public MapsTab getMapsTab() {
        return mapsTab;
    }

    public void setMapsTab(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }
}
