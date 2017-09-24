package com.connection.prem.connection.Location;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Prem on 4/29/17.
 */

public class Neighbour extends Service implements LocationListener {

    private final Context m_Context;
    private Activity m_Activity; // This is variable

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetNetwork = false;

    Location  location;
    double longitute;
    double latitute;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10; // 10 Meters
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1; // 1 minutes

    private static final int REQEST_PERMISSION_LOCATION_STATE  = 1;

    protected LocationManager locationManager;

    //Setting Context
    public Neighbour(Context aContext, Activity aActivity){
         this.m_Context = aContext;
         locationManager = (LocationManager)m_Context.getSystemService(LOCATION_SERVICE);
         isPermissionCheck(aActivity);
    }

    public boolean checkPermission(Activity aActivity){
            if(ContextCompat.checkSelfPermission(this.m_Context,  Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(aActivity, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return false;
            }
        return  true;
    }


    public Location getLocation() {
            try{
                //locationManager = (LocationManager)m_Context.getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);



                if(isGPSEnabled && isNetworkEnabled){

                    canGetNetwork = true;

                    if(isNetworkEnabled){
                        // Request for permission


                        locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
                                                                MIN_TIME_BETWEEN_UPDATES,
                                                                MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitute = location.getLatitude();
                                longitute = location.getLongitude();
                            }
                        }
                    }

                    if(isGPSEnabled) {
                        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                                MIN_TIME_BETWEEN_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitute = location.getLatitude();
                                longitute = location.getLongitude();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    } // End of getLocation


    public boolean isPermissionCheck(Activity myActivity){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(myActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(myActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(myActivity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQEST_PERMISSION_LOCATION_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission Granted Part

        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQEST_PERMISSION_LOCATION_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public  void onLocationChanged(Location aLocation){

    }


    @Override
    public void onProviderDisabled(String aProvider){


    }

    @Override
    public  void onProviderEnabled(String aProvider){


    }

    @Override
    public  void onStatusChanged(String aProvider, int aStatus, Bundle extras){

    }


    @Override
    public IBinder onBind(Intent arg){

            return null;
    }


    //Accessors

    public double getLongitute(){
        if(location != null)
            longitute = location.getLatitude();
        return longitute;
    }


     public double getLatitute(){
         if(location !=  null)
                latitute  = location.getLatitude();
         return  latitute;
     }


} // End of Neighbour Class

