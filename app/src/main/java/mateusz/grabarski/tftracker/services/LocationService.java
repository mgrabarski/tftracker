package mateusz.grabarski.tftracker.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import javax.inject.Inject;

import mateusz.grabarski.tftracker.base.App;
import mateusz.grabarski.tftracker.base.AppSettings;
import mateusz.grabarski.tftracker.base.MainBus;
import mateusz.grabarski.tftracker.base.listeners.AppSettingsObserver;
import mateusz.grabarski.tftracker.services.events.CurrentLocationEvent;
import mateusz.grabarski.tftracker.services.interfaces.LocationInterface;

/**
 * Created by MGrabarski on 07.02.2018.
 */

public class LocationService extends Service implements LocationInterface, AppSettingsObserver {

    private LocationListener mLocationListener;

    @Inject
    AppSettings appSettings;

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
        MainBus.getBus().register(this);
        mLocationListener = new LocationListener(this, getApplicationContext());
        ((App) getApplicationContext()).getApplicationComponent().inject(this);
        appSettings.addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainBus.getBus().unregister(this);
        mLocationListener.cleanUp();
    }

    @Override
    public void currentDeviceLocation(Location location) {
        postOnMain(new CurrentLocationEvent(location));
    }

    private void postOnMain(Object object) {
        MainBus.getBus().post(object);
    }

    @Override
    public void onTrackingChange(boolean tracking) {
        // TODO: 10.02.2018 turn on/off tracking
    }
}
