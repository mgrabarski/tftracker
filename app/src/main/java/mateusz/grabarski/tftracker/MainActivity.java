package mateusz.grabarski.tftracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mateusz.grabarski.tftracker.services.LocationService;
import mateusz.grabarski.tftracker.utils.GoogleServiceChecker;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (GoogleServiceChecker.isGoogleServicesOk(this))
            startService(new Intent(this, LocationService.class));
    }
}
