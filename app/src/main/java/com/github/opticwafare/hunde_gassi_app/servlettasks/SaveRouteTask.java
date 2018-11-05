package com.github.opticwafare.hunde_gassi_app.servlettasks;

import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.google.gson.Gson;

public class SaveRouteTask extends SendToServletTask {

    private Route route;

    public SaveRouteTask() {
        setServletName("SaveRoute");
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
        Gson gson = new Gson();
        setUrlParameters("route="+gson.toJson(this.route));
    }

    @Override
    protected void onPostExecute(String serverResponse) {
        // TODO toast
        System.out.println("Server Response from SaveRouteTask: "+serverResponse);
    }
}
