package com.example.marwa.androidproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends Activity {
    Intent intent;
    TextView name,type;
    ArrayList<Trip> TripList = new ArrayList<>();
    TripAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.tripname);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        //ref = database.getReference("server/saving-data/fireblog/posts");


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        adapter = new TripAdapter(MainActivity.this,TripList);

        RecyclerView.LayoutManager manger = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manger);
        recyclerView.setAdapter(adapter);


        ref.child("trip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.getValue().toString());
                TripList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Trip trip  = snapshot.getValue(Trip.class);
                    TripList.add(trip);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
                }

                Collections.reverse(TripList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








       /* RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new LinearLayoutManager(this));




       /* TripAdapter ca = new TripAdapter(TripList);
        recList.setAdapter(ca);
*/


        intent=new Intent(this,MainActivity.class);
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





}
