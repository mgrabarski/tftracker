package mateusz.grabarski.tftracker.base.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mateusz.grabarski.tftracker.base.AppSettings;
import mateusz.grabarski.tftracker.base.Constants;

/**
 * Created by MGrabarski on 10.02.2018.
 */
@Singleton
@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public AppSettings provideAppSettings(SharedPreferences sharedPreferences) {
        return new AppSettings(sharedPreferences);
    }
}
