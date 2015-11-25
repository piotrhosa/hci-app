package uk.ac.gla.bikepool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tomazz on 25/11/15.
 */
public class AlarmReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        BikePoolController controller = (BikePoolController) context.getApplicationContext();
        String bikerName = intent.getStringExtra("bikerName");
        String startingLocation = intent.getStringExtra("startLocation");
        controller.buildAndSendNotification(bikerName+" get your bike! :D",
                "We'll be leaving from " + startingLocation + " in 20 minutes");
    }
}
