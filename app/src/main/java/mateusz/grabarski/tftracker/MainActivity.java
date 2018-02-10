package mateusz.grabarski.tftracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.base.App;
import mateusz.grabarski.tftracker.base.AppSettings;
import mateusz.grabarski.tftracker.base.BaseActivity;
import mateusz.grabarski.tftracker.base.Constants;
import mateusz.grabarski.tftracker.services.LocationService;
import mateusz.grabarski.tftracker.services.events.CurrentLocationEvent;
import mateusz.grabarski.tftracker.utils.GoogleServiceChecker;
import mateusz.grabarski.tftracker.utils.Permissions;

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
    public void event(CurrentLocationEvent event) {
        moveCamera(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()), Constants.DEFAULT_ZOOM);
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}
