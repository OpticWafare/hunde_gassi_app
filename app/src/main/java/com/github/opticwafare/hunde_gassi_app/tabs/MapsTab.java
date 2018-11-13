package com.github.opticwafare.hunde_gassi_app.tabs;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.MapsTabSpinnerListener;
import com.github.opticwafare.hunde_gassi_app.NewRouteButtonListener;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.locationupdater.PolyLineUpdater;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTask;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTaskFake;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTimer;
import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.github.opticwafare.hunde_gassi_app.servlettasks.GetMyRoutesTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.LocationServices;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsTab extends SuperTab implements OnMapReadyCallback {

    private static final String TAG = "MapsTab";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final float DEFAULT_ZOOM = 15f;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;

    private Button btnNewRoute;
    private Spinner spinner;
    private Polyline currentRoute;
    private Date currentRouteStartTime;

    private int mapStatus = 0;
    private Route[] walkedRoutes;

    private UpdateLocationTimer currentUpdateLocationTimer;

    public MapsTab() {
        super("Maps", R.layout.activity_maps);
    }

    @Override
    public void init(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        btnNewRoute = (Button) mainActivity.findViewById(R.id.btnNewRoute);
        btnNewRoute.setOnClickListener(new NewRouteButtonListener(this));

        spinner = (Spinner) mainActivity.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new MapsTabSpinnerListener(this));

        GetMyRoutesTask getMyRoutesTask = new GetMyRoutesTask();
        getMyRoutesTask.setMapsTab(this);
        getMyRoutesTask.execute("");

        if(isServicesOK()){
            startMapsGUI();
        }
    }

    public void startMapsGUI() {

        Log.d(TAG, "starting maps GUI");
        getLocationPermission();
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mainActivity);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(mainActivity, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(mainActivity, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity);

        try{
            if(mainActivity.getmLocationPermissionsGranted()){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(mainActivity, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mainActivity.setmLocationPermissionsGranted(true);
                initMap();
            }else{
                ActivityCompat.requestPermissions(mainActivity,
                        permissions,
                        MainActivity.LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(mainActivity,
                    permissions,
                    MainActivity.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(mainActivity, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mainActivity.getmLocationPermissionsGranted()) {
            //getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }



            // Add polylines and polygons to the map. This section shows just
            // a single polyline. Read the rest of the tutorial to learn more.
            /*currentRoute = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(startPoint));*/

            /*List<LatLng> points = polyline1.getPoints();
            points.add(new LatLng(100, 100));
            polyline1.setPoints(points);*/


            /*UpdateLocationTimer timer = new UpdateLocationTimer();

            PolyLineUpdater polyLineUpdater = new PolyLineUpdater(currentRoute, mainActivity);
            timer.addListener(polyLineUpdater);

            UpdateLocationTask timerTask = new UpdateLocationTaskFake(startPoint, 0.5, 0.5);
            timer.schedule(timerTask, TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(5));*/

            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
            //mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    public void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    public int getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(int mapStatus) {
        this.mapStatus = mapStatus;
    }

    public Button getBtnNewRoute() {
        return btnNewRoute;
    }

    public Polyline getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Polyline currentRoute) {
        this.currentRoute = currentRoute;
    }

    public Date getCurrentRouteStartTime() {
        return currentRouteStartTime;
    }

    public void setCurrentRouteStartTime(Date currentRouteStartTime) {
        this.currentRouteStartTime = currentRouteStartTime;
    }

    public Route[] getWalkedRoutes() {
        return walkedRoutes;
    }

    public void setWalkedRoutes(Route[] walkedRoutes) {
        this.walkedRoutes = walkedRoutes;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public UpdateLocationTimer getCurrentUpdateLocationTimer() {
        return currentUpdateLocationTimer;
    }

    public void setCurrentUpdateLocationTimer(UpdateLocationTimer currentUpdateLocationTimer) {
        this.currentUpdateLocationTimer = currentUpdateLocationTimer;
    }
}
