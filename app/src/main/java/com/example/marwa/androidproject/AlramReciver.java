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


        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, com.example.marwa.androidproject.AlarmDialog.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(trIntent);





    }

}
