package mateusz.grabarski.tftracker.base;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by MGrabarski on 09.02.2018.
 */

public class MainBus {

    public static final Bus bus = new ApplicationBus();

    private MainBus() {

    }

    public static Bus getBus() {
        return bus;
    }

    private static class ApplicationBus extends Bus {

        private final Handler mainThread = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mainThread.post(new Runnable() {

                    @Override
                    public void run() {
                        post(event);
                    }
                });
            }
        }
    }
}
