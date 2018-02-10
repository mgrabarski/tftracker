package mateusz.grabarski.tftracker.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper instance = null;

    public static String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Route, Long> routeDao;
    private Dao<RouteLocation, Long> routeLocationsDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d(TAG, "onCreate: ");

        try {
            TableUtils.createTable(connectionSource, Route.class);
            TableUtils.createTable(connectionSource, RouteLocation.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }

    @Override
    public void close() {
        super.close();

        routeDao = null;
        routeLocationsDao = null;
    }

    public Dao<Route, Long> getRouteDao() {
        if (routeDao == null)
            try {
                routeDao = getDao(Route.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return routeDao;
    }

    public Dao<RouteLocation, Long> getRouteLocationsDao() {
        if (routeLocationsDao == null)
            try {
                routeLocationsDao = getDao(RouteLocation.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return routeLocationsDao;
    }
}
