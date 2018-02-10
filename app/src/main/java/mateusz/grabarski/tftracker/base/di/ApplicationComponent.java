package mateusz.grabarski.tftracker.base.di;

import javax.inject.Singleton;

import dagger.Component;
import mateusz.grabarski.tftracker.MainActivity;
import mateusz.grabarski.tftracker.services.LocationService;

/**
 * Created by MGrabarski on 10.02.2018.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(LocationService service);
}
