package mateusz.grabarski.tftracker.base;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import mateusz.grabarski.tftracker.base.listeners.AppSettingsObserver;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class AppSettings {

    private static final String KEY_TRACKING = "tracking";

    private SharedPreferences mSharedPreferences;
    private List<AppSettingsObserver> mObservers;

    public AppSettings(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
        mObservers = new ArrayList<>();
    }

    public void addObserver(AppSettingsObserver observer) {
        if (!mObservers.contains(observer))
            mObservers.add(observer);
    }

    public void removeObserver(AppSettingsObserver observer) {
        mObservers.remove(observer);
    }

    public void setTracking(boolean tracking) {
        mSharedPreferences.edit().putBoolean(KEY_TRACKING, tracking).apply();

        for (AppSettingsObserver observer : mObservers)
            observer.onTrackingChange(tracking);
    }

    public boolean getTracking() {
        return mSharedPreferences.getBoolean(KEY_TRACKING, false);
    }
}
