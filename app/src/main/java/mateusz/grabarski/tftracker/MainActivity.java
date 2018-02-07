package mateusz.grabarski.tftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mateusz.grabarski.tftracker.services.LocationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, LocationService.class));
    }
}
