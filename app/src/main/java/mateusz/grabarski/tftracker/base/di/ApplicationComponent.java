package mateusz.grabarski.tftracker.base.di;

import javax.inject.Singleton;

import dagger.Component;
import mateusz.grabarski.tftracker.data.database.managers.RouteManager;
import mateusz.grabarski.tftracker.services.LocationService;
import mateusz.grabarski.tftracker.views.list.RouteListActivity;
import mateusz.grabarski.tftracker.views.main.MainActivity;

/**
 * Created by MGrabarski on 10.02.2018.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(RouteListActivity activity);
    void inject(LocationService service);
    void inject(RouteManager routeManager);
}
