package com.connection.prem.location;

/**
 * Created by prem on 9/7/17.
 */
import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


public class GPSTracker implements  LocationListener {
    private Context m_CurrentContext;
    private Activity m_CurrentActivity;

    GPSTracker(Context aCurrentContext, Activity aCurrentActivity){
        m_CurrentContext = aCurrentContext;
        m_CurrentActivity = aCurrentActivity;
    }

    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(m_CurrentContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(m_CurrentContext, "Please Enable GPS", Toast.LENGTH_LONG).show();
            // Idea is to provide a way to let em enable it
            if(ActivityCompat.shouldShowRequestPermissionRationale( m_CurrentActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
                ActivityCompat.requestPermissions(m_CurrentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

            return  null; // Currently Returning Null
        } else {
            LocationManager m_LocationManager = (LocationManager) m_CurrentContext.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = m_LocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isGPSEnabled){
                m_LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
                Location m_CurrentLocation =  m_LocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return  m_CurrentLocation;
            } else {
                Toast.makeText(m_CurrentContext, "GPS is Disabled", Toast.LENGTH_LONG).show();
                // Idea is to provide a way to let em enable it
                return  null; // Currently Returning Null
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
