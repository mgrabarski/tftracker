package mateusz.grabarski.tftracker.views.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.R;
import mateusz.grabarski.tftracker.base.App;
import mateusz.grabarski.tftracker.base.AppSettings;
import mateusz.grabarski.tftracker.base.BaseActivity;
import mateusz.grabarski.tftracker.base.Constants;
import mateusz.grabarski.tftracker.base.MainBus;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;
import mateusz.grabarski.tftracker.services.LocationService;
import mateusz.grabarski.tftracker.services.events.CurrentLocationEvent;
import mateusz.grabarski.tftracker.services.events.CurrentRouteTruckedEvent;
import mateusz.grabarski.tftracker.utils.GoogleServiceChecker;
import mateusz.grabarski.tftracker.utils.Permissions;
import mateusz.grabarski.tftracker.views.list.RouteListActivity;

public class MainActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";

    @BindView(R.id.activity_main_tracking_switch)
    Switch trackingSwitch;

    @Inject
    AppSettings appSettings;

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trackingSwitch.setChecked(appSettings.getTracking());

        trackingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettings.setTracking(isChecked);
            }
        });

        if (appSettings.getTracking())
            MainBus.getBus().post(new CurrentRouteTruckedEvent());

        if (GoogleServiceChecker.isGoogleServicesOk(this))
            checkLocationPermission();
    }

    @Override
    protected void inject() {
        ((App) getApplicationContext()).getApplicationComponent().inject(this);
    }

    private void checkLocationPermission() {
        if (Permissions.checkLocationPermission(this))
            initMap();

        // TODO: 08.02.2018 else consider grand location permission dialog
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_map);
        mapFragment.getMapAsync(this);

        initLocationService();
    }

    private void initLocationService() {
        startService(new Intent(this, LocationService.class));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (Permissions.isPermissionGranted(this)) {
            return;
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Subscribe
    public void onCurrentLocationChange(CurrentLocationEvent event) {
        moveCamera(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()), Constants.DEFAULT_ZOOM);

        mGoogleMap.
    }

    @Subscribe
    public void onCurrentRouteLocationUpdate(Route route) {
        refreshPathOnMap(route);
    }

    private void refreshPathOnMap(Route route) {
        List<LatLng> locations = new ArrayList<>();

        for (RouteLocation routeLocation : route.getLocations())
            locations.add(new LatLng(routeLocation.getLatitude(), routeLocation.getLongitude()));

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(locations);

        if (locations.size() > 0)
            mGoogleMap.addPolyline(polylineOptions);
        else
            mGoogleMap.clear();
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.activity_maine_menu_history:
                Intent intent = new Intent(this, RouteListActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
