package mateusz.grabarski.tftracker.data.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MGrabarski on 10.02.2018.
 */
@DatabaseTable(tableName = "ROUTE")
public class Route implements Serializable {

    public static final String ID_COLUMN = "ID";
    public static final String CREATE_TS_COLUMN = "CREATE_TIME";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    private long id;

    @DatabaseField(columnName = CREATE_TS_COLUMN)
    private long createTimestamp;

    @ForeignCollectionField(eager = true)
    private Collection<RouteLocation> locations;

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

    public Collection<RouteLocation> getLocations() {
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
