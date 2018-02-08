package mateusz.grabarski.tftracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import mateusz.grabarski.tftracker.services.LocationService;
import mateusz.grabarski.tftracker.utils.GoogleServiceChecker;
import mateusz.grabarski.tftracker.utils.Permissions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (GoogleServiceChecker.isGoogleServicesOk(this))
            checkLocationPermission();
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

    }
}
