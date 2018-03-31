package com.example.marwa.androidproject;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

/**
 * Created by esraa on 3/28/18.
 */

public class AlramReciver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "";
    FragmentManager manager;
    private static final String PREFS_NAME = "trip";
Intent intent2;
    String trip_name;
    String trip_note;
    @Override
    public void onReceive(Context context, Intent intent) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//

        SharedPreferences prefs =context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);


        trip_name = prefs.getString("Trip_name","no trip name");
        trip_note = prefs.getString("Trip_context","no detail");


        Toast.makeText(context, trip_name+" "+trip_note, Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(trip_name);
        builder.setContentText(trip_note);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);


        builder.addAction(R.mipmap.ic_launcher, "Ok",
                pendingIntent);
        builder.addAction(R.mipmap.ic_launcher, "Later",
                pendingIntent);
        builder.addAction(R.mipmap.ic_launcher, "Cancel",
                pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int notificationId = 1;
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationManager.notify(notificationId, builder.build());



    }

}
