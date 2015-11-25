package uk.ac.gla.bikepool;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

/**
 * Created by tomazz on 25/11/15.
 */
public class BikePoolController extends Application{
    public static final String TAG = BikePoolController.class.getSimpleName();
    private static BikePoolController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    public void buildAndSendNotification(String messageBody,String messageTitle){
        int notificationId = 001;
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_media_play)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

}
