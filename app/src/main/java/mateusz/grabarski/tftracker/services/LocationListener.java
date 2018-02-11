package mateusz.grabarski.tftracker.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import mateusz.grabarski.tftracker.base.Constants;
import mateusz.grabarski.tftracker.services.interfaces.LocationInterface;

/**
 * Created by MGrabarski on 07.02.2018.
 */

public class LocationListener implements android.location.LocationListener {

    private static final String TAG = "LocationListener";

    private LocationManager mLocationManager = null;

    private Context mContext;
    private Location mLastLocation;
    private LocationInterface mListener;

    public LocationListener(LocationInterface listener, Context context) {
        this.mListener = listener;
        this.mContext = context;

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mLocationManager != null) {
            Criteria criteria = getCriteria();
            mLocationManager.getBestProvider(criteria, true);

            try {
                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        Constants.LOCATION_INTERVAL,
                        Constants.LOCATION_DISTANCE_CHANGE,
                        this);
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        Constants.LOCATION_INTERVAL,
                        Constants.LOCATION_DISTANCE_CHANGE,
                        this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setBearingAccuracy(Criteria.ACCURACY_LOW);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        return criteria;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location);
        mLastLocation = location;
        mListener.currentDeviceLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled: " + provider);
    }

    public void cleanUp() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }

    public void stopTracking() {
        cleanUp();
    }

    @SuppressLint("MissingPermission")
    public void updateLocation() {
        Criteria criteria = getCriteria();
        mLocationManager.requestSingleUpdate(criteria, this, null);
    }
}
