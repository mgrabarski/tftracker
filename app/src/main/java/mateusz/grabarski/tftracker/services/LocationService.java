package mateusz.grabarski.tftracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import mateusz.grabarski.tftracker.services.interfaces.LocationInterface;

/**
 * Created by MGrabarski on 07.02.2018.
 */

public class LocationService extends Service implements LocationInterface {

    private LocationListener mLocationListener;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        mLocationListener = new LocationListener(this, getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationListener.cleanUp();
    }

    @Override
    public void noPermissionsGranted() {

    }
}
