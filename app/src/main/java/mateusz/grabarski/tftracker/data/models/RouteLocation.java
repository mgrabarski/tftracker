package mateusz.grabarski.tftracker.data.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by MGrabarski on 10.02.2018.
 */
@DatabaseTable(tableName = "LOCATION")
public class RouteLocation {

    public static final String ID_COLUMN = "ID";
    public static final String TIME_COLUMN = "TIME";
    public static final String LATITUDE_COLUMN = "LATITUDE";
    public static final String LONGITUDE_COLUMN = "LONGITUDE";
    public static final String ROUTE_ID_COLUMN = "ID_ROUTE";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    private long id;

    @DatabaseField(columnName = TIME_COLUMN)
    private long time;

    @DatabaseField(columnName = LATITUDE_COLUMN)
    private double latitude;

    @DatabaseField(columnName = LONGITUDE_COLUMN)
    private double longitude;

    @DatabaseField(columnName = ROUTE_ID_COLUMN, foreign = true)
    private Route route;

    public RouteLocation() {
    }

    public RouteLocation(long time, double latitude, double longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "RouteLocation{" +
                "time=" + time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
