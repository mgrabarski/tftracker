package mateusz.grabarski.tftracker.utils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import mateusz.grabarski.tftracker.base.Constants;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;

/**
 * Created by MGrabarski on 12.02.2018.
 */

public class MapUtils {

    public static void moveCamera(LatLng latLng, GoogleMap googleMap) {
        if (googleMap == null || latLng == null)
            return;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constants.DEFAULT_ZOOM));
    }

    public static void drawRoutePath(Route route, GoogleMap googleMap) {
        List<LatLng> locations = new ArrayList<>();

        for (RouteLocation routeLocation : route.getLocations())
            locations.add(new LatLng(routeLocation.getLatitude(), routeLocation.getLongitude()));

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(locations);

        if (locations.size() > 0)
            googleMap.addPolyline(polylineOptions);
        else
            googleMap.clear();
    }
}
