package mateusz.grabarski.tftracker.data.database.managers;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import mateusz.grabarski.tftracker.data.database.DatabaseHelper;
import mateusz.grabarski.tftracker.data.database.managers.interfaces.RouteManagerInterface;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class RouteManager implements RouteManagerInterface {

    @Inject
    DatabaseHelper databaseHelper;

    public RouteManager(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void insert(Route route) throws SQLException {
        databaseHelper.getRouteDao().create(route);
    }

    @Override
    public void update(Route route) throws SQLException {
        databaseHelper.getRouteDao().update(route);
    }

    @Override
    public void delete(Route route) throws SQLException {
        databaseHelper.getRouteDao().delete(route);
    }

    @Override
    public void addLocationToRoute(Route route, RouteLocation location) throws SQLException {
        location.setRoute(route);
        databaseHelper.getRouteLocationsDao().create(location);
    }

    @Override
    public List<Route> getAllRoutes() throws SQLException {
        return databaseHelper.getRouteDao().queryForAll();
    }
}
