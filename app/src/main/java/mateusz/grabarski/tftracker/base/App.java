package mateusz.grabarski.tftracker.base;

import android.app.Application;

import mateusz.grabarski.tftracker.base.di.ApplicationComponent;
import mateusz.grabarski.tftracker.base.di.ApplicationModule;
import mateusz.grabarski.tftracker.base.di .DaggerApplicationComponent;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationLifecycleHandler applicationLifecycleHandler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(applicationLifecycleHandler);
        registerComponentCallbacks(applicationLifecycleHandler);
    }

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null)
            mApplicationComponent = DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();

        return mApplicationComponent;
    }
}
