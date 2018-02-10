package mateusz.grabarski.tftracker.base;

import android.content.SharedPreferences;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class AppSettings {

    private static final String KEY_TRACKING = "tracking";

    private SharedPreferences mSharedPreferences;

    public AppSettings(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public void setTracking(boolean tracking) {
        mSharedPreferences.edit().putBoolean(KEY_TRACKING, tracking).apply();
    }

    public boolean getTracking() {
        return mSharedPreferences.getBoolean(KEY_TRACKING, false);
    }
}
