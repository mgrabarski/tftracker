package mateusz.grabarski.tftracker.data.database.managers.interfaces;

import java.sql.SQLException;
import java.util.List;

import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public interface RouteManagerInterface {
    void insert(Route route) throws SQLException;
    void update(Route route) throws SQLException;
    void delete(Route route) throws SQLException;
    void addLocationToRoute(Route route, RouteLocation location) throws SQLException;
    List<Route> getAllRoutes() throws SQLException;
}
