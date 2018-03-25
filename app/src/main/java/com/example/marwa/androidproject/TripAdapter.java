package com.example.marwa.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marwa on 24/03/18.
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
    public void onBindViewHolder(TripHolder  holder, int position) {
        final Trip student = tripList.get(position);
        holder.tripname.setText(student.getTripName());
        holder.tripdate.setText(student.getDate());
        holder.tripnotes.setText(student.getNotes());
        holder.triptype.setText(student.getTripType());

        holder.tripname.setText(student.getTripName() );
       /* holder.editStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditStudentActivity.class);
                intent.putExtra("name", student.getName());
                context.startActivity(intent);
            }*/

    }

    @Override
    public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row, parent, false);
        return new TripHolder(row);
        /*View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_row, viewGroup, false);

        return new TripHolder(itemView);*/
    }

    public static class TripHolder extends RecyclerView.ViewHolder {


        protected TextView tripname;
        protected TextView triptype;
        protected TextView tripdate;
        protected TextView tripnotes;

        public TripHolder(View v) {
            super(v);
            tripname =  (TextView) v.findViewById(R.id.tripname);
            triptype = (TextView)  v.findViewById(R.id.triptype);
            tripdate = (TextView)  v.findViewById(R.id.tripdate);
            tripnotes = (TextView) v.findViewById(R.id.tripnotes);
        }
    }
}
