package com.example.marwa.androidproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "trip";
    Intent intent;

    TextView name,type;
    ArrayList<Trip> TripList = new ArrayList<>();
    TripAdapter adapter;
    DatabaseReference ref;
    RecyclerView  recyclerView;
    RecyclerView.LayoutManager manger;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent alarmIntent = new Intent(MainActivity.this, AlramReciver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        name=findViewById(R.id.tripname);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new TripAdapter(MainActivity.this,TripList);
        manger = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
       // prepareMovieData();

        ref.child("trips").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, dataSnapshot.getValue().toString());
             TripList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Trip trip  = snapshot.getValue(Trip.class);
                    TripList.add(trip);
                    adapter.notifyDataSetChanged();
                    if (trip.getDate()!=null) {


                        //split date
                        String[] separated = trip.getDate().split("-");
                        String trip_date = separated[0];
                        String trip_time = separated[1];
                        String[] time_seperated = trip_time.split(":");
                        String[] date_seperated = trip_date.split("/");
                        String trip_day = date_seperated[0];
                        String trip_month = date_seperated[1];
                        String trip_year = date_seperated[2];

                        String trip_hour = time_seperated[0];
                        String trip_min = time_seperated[1];


                        //to get time now
                        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
                        Date date = new Date();
                        //System.out.println(dateFormat.format(date));
                        DateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                        Date strDate = null;
                        Calendar calendar = Calendar.getInstance();
                        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        int minOfDay = calendar.get(Calendar.MINUTE);

                        String s = dateFormat.format(date).toString();

                        //if (s.equals(trip_date)) {
                            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            int interval = 1000 * 60 * 30;
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTimeInMillis(System.currentTimeMillis());
                            calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(trip_day));
                            calendar2.set(Calendar.MONTH, Integer.parseInt(trip_month));
                            calendar2.set(Calendar.YEAR, Integer.parseInt(trip_year));
                            calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trip_hour));
                            calendar2.set(Calendar.MINUTE, Integer.parseInt(trip_min));

                            Intent i = new Intent(getApplicationContext(), AlramReciver.class);
                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Trip_name", trip.getTripName());
                            editor.putString("Trip_context", trip.getNotes());
                            editor.commit();


                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);

                            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                                    interval, pendingIntent);


                        //}
                    }

                }


                recyclerView.setLayoutManager(manger);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

                 Collections.reverse(TripList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: cancel");

            }
        });
        intent=new Intent(this,Main2Activity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 startActivity(intent);
            }
        });




    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 16000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try

        {
            Date date = format.parse(dtStart);
            System.out.println(date);
            Log.i("TAG","date"+date);
        } catch(
                ParseException e)

        {
            e.printStackTrace();
        }
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
       // Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAlarmAt() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 55);
        Intent intent = new Intent(getApplicationContext(), AlramReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);


    }




  /*  private void prepareMovieData() {
        Trip movie = new Trip("ant", "personal", "2015","hhhh","cairo","egypt");
       TripList.add(movie);
        Trip movie2 = new Trip("ant", "personal", "2015","hhhh","cairo","egypt");
        TripList.add(movie2);
    }*/

}
