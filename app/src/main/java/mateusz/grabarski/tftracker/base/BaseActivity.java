package mateusz.grabarski.tftracker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBus.getBus().register(this);
        inject();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainBus.getBus().unregister(this);
    }

    protected abstract void inject();
}
