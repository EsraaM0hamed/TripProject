package com.example.marwa.androidproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    TextView name,type;
    ArrayList<Trip> TripList = new ArrayList<>();
    TripAdapter adapter;
    DatabaseReference ref;
    RecyclerView  recyclerView;
    RecyclerView.LayoutManager manger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    Toast.makeText(MainActivity.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
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



  /*  private void prepareMovieData() {
        Trip movie = new Trip("ant", "personal", "2015","hhhh","cairo","egypt");
       TripList.add(movie);
        Trip movie2 = new Trip("ant", "personal", "2015","hhhh","cairo","egypt");
        TripList.add(movie2);
    }*/

}
