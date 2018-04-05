package com.example.marwa.androidproject;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

/**
 * Created by esraa on 4/1/18.
 */

public class AlarmDialog extends Activity {
    private static final String LOG_TAG = "SMSReceiver";
    public static final int NOTIFICATION_ID_RECEIVED = 0x1221;
    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final String CHANNEL_ID = "myChannel";
    boolean flag=false;

    private static final String PREFS_NAME = "trip";
    NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
    Ringtone r;
    String trip_name;
    String trip_note;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs =this.getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);


        trip_name = prefs.getString("Trip_name","no trip name");
        trip_note = prefs.getString("Trip_context","no detail");
        intent = new Intent(this, AlarmDialog.class);


        //setContentView(R.layout.main);
        final Context ctx = this;

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(trip_name).setCancelable(
                false).setPositiveButton("Start",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).setNeutralButton("Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        displayNotifaction();
                        r.stop();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                r.stop();
                NotificationManagerCompat.from(ctx).cancel(1);

            }
        });


        AlertDialog alert = builder.create();

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r= RingtoneManager.getRingtone(this, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        alert.show();
    }





    private void displayNotifaction() {


        PendingIntent pendingIntent = PendingIntent.getActivity(AlarmDialog.this, 0,intent, 0);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(trip_name);
        builder.setContentText(trip_note);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);
        builder.setBadgeIconType(R.drawable.ic_launcher_background);
        builder.setOngoing(true);
        builder. setStyle(new NotificationCompat.BigTextStyle()
        );

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId=1;

        notificationManager.notify(notificationId, builder.build());



    }



    /*
     *   PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        SharedPreferences prefs =context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
//
//
//        trip_name = prefs.getString("Trip_name","no trip name");
//        trip_note = prefs.getString("Trip_context","no detail");
//
//
//        Toast.makeText(context, trip_name+" "+trip_note, Toast.LENGTH_SHORT).show();
//
//
//
      * */
//
//    private final BroadcastReceiver mReceivedSMSReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            if (ACTION.equals(action)) {
//                //your SMS processing code
//                displayAlert();
//            }
//        }
//    };

}