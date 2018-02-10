package mateusz.grabarski.tftracker.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import java.sql.SQLException;

import javax.inject.Inject;

import mateusz.grabarski.tftracker.base.App;
import mateusz.grabarski.tftracker.base.AppSettings;
import mateusz.grabarski.tftracker.base.MainBus;
import mateusz.grabarski.tftracker.base.listeners.AppSettingsObserver;
import mateusz.grabarski.tftracker.data.database.managers.interfaces.RouteManagerInterface;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;
import mateusz.grabarski.tftracker.services.events.CurrentLocationEvent;
import mateusz.grabarski.tftracker.services.interfaces.LocationInterface;

/**
 * Created by MGrabarski on 07.02.2018.
 */

public class LocationService extends Service implements LocationInterface, AppSettingsObserver {

    private LocationListener mLocationListener;
    private Route mCurrentRoute;

    @Inject
    AppSettings appSettings;

    @Inject
    RouteManagerInterface routeManager;

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
        Log.d(LocationService.class.getSimpleName(), "onCreate: ");
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

        if (mCurrentRoute != null) {
            RouteLocation routeLocation = new RouteLocation(location.getTime(),
                    location.getLatitude(),
                    location.getLongitude());

            mCurrentRoute.addNewRouteLocation(routeLocation);

            try {
                routeManager.addLocationToRoute(mCurrentRoute, routeLocation);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            postOnMain(mCurrentRoute);
        }
    }

    private void postOnMain(Object object) {
        MainBus.getBus().post(object);
    }

    @Override
    public void onTrackingChange(boolean tracking) {
        if (tracking) {
            mCurrentRoute = new Route(System.currentTimeMillis());
            try {
                routeManager.insert(mCurrentRoute);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            postOnMain(mCurrentRoute);
        } else {
            // TODO: 10.02.2018 save route
            mCurrentRoute = null;
        }
    }
}
