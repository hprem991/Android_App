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

    protected LocationManager locationManager;

    //Setting Context
    public Neighbour(Context aContext, Activity aActivity){
         this.m_Context = aContext;
         locationManager = (LocationManager)m_Context.getSystemService(LOCATION_SERVICE);
         if(checkPermission(aActivity))
            getLocation();
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

