package mateusz.grabarski.tftracker.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class Route {

    private long id;
    private long createTimestamp;
    private List<RouteLocation> locations;

    public Route() {
        locations = new ArrayList<>();
    }

    public Route(long createTimestamp) {
        this();
        this.createTimestamp = createTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public List<RouteLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteLocation> locations) {
        this.locations = locations;
    }

    public void addNewRouteLocation(RouteLocation routeLocation) {
        locations.add(routeLocation);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", createTimestamp=" + createTimestamp +
                ", locations=" + locations +
                '}';
    }
}
