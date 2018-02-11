package mateusz.grabarski.tftracker.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by MGrabarski on 07.02.2018.
 */

public class GoogleServiceChecker {

    private static final String TAG = "GoogleServiceChecker";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static boolean isGoogleServicesOk(Activity activity) {
        Log.d(TAG, "isGoogleServicesOk: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isGoogleServicesOk: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isGoogleServicesOk: Google Play Services is not working but user can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }

        return false;
    }
}
