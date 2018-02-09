package mateusz.grabarski.tftracker.services.events;

import android.location.Location;

/**
 * Created by MGrabarski on 09.02.2018.
 */

public class CurrentLocationEvent {

    private Location location;

    public CurrentLocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
