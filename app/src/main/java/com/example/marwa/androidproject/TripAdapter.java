package com.example.marwa.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by marwa on 21/03/18.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder>
{
    private List<Trip> tripList;
    private Context context;




    public TripAdapter(Context context,List<Trip> contactList) {
        this.tripList = contactList;
        this.context=context;
    }


    @Override
    public int getItemCount() {
        return tripList.size();
    }

    @Override
    public void onBindViewHolder(final TripHolder  holder, int position) {
        final Trip trip = tripList.get(position);
       // Toast.makeText(context, trip.getId(), Toast.LENGTH_SHORT).show();
        holder.tripname.setText(trip.getTripName());
        holder.tripdate.setText(trip.getDate());
        holder.tripnotes.setText(trip.getNotes());
        holder.triptype.setText(trip.getTripType());

        holder.tripname.setText(trip.getTripName() );

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, details.class);
                intent.putExtra("holder",trip.getTripName());
                context.startActivity(intent);
            }
            });


    }

    @Override
    public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row, parent, false);
        return new TripHolder(row);


    }

    public static class TripHolder extends RecyclerView.ViewHolder {


        protected TextView tripname;
        protected TextView triptype;
        protected TextView tripdate;
        protected TextView tripnotes;
        protected CardView card;


        public TripHolder(View v) {
            super(v);
            tripname =  (TextView) v.findViewById(R.id.tripname);
            triptype = (TextView)  v.findViewById(R.id.triptype);
            tripdate = (TextView)  v.findViewById(R.id.tripdate);
            tripnotes = (TextView) v.findViewById(R.id.tripnotes);
            card=(CardView) v.findViewById(R.id.card);
        }
    }
}
