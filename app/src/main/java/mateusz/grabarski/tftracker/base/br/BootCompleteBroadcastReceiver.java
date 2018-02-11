package mateusz.grabarski.tftracker.base.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mateusz.grabarski.tftracker.services.LocationService;

/**
 * Created by MGrabarski on 10.02.2018.
 */

public class BootCompleteBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, LocationService.class));
    }
}
