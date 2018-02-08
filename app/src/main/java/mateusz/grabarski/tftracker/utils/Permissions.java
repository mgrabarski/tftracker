package mateusz.grabarski.tftracker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by MGrabarski on 08.02.2018.
 */

public class Permissions {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static boolean checkLocationPermission(Activity activity) {
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(activity, FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(activity, COURSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                ActivityCompat.requestPermissions(activity,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
    }
}
