package com.example.marwa.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class details extends AppCompatActivity {

    private static final String TAG="" ;
    Trip tr;
    ArrayList t;
    TextView name;
    TextView  note;
    TextView type;
    TextView date;
    TextView start;
    TextView end;
    Button delete;
    Button edit;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       final String x= getIntent().getStringExtra("holder");

       Toast.makeText(this, x, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("trips");

        name =  findViewById(R.id.name);
        note =  findViewById(R.id.note);
        type =  findViewById(R.id.textView9);
         date =  findViewById(R.id.date);
        start =  findViewById(R.id.start);
         end =  findViewById(R.id.end);
         delete=findViewById(R.id.delete);
         edit=findViewById(R.id.edit);

         final Query query = ref.orderByChild("tripName").equalTo(x);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    tr = snapshot.getValue(Trip.class);//.notifyDataSetChanged();
                    name.setText(tr.getTripName());
                    note.setText(tr.getNotes());
                    date.setText(tr.getDate());
                    type.setText(tr.getTripType());
                    start.setText(tr.getStartPoint());
                    end.setText(tr.getEndPoint());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // Toast.makeText(details.this, "delete", Toast.LENGTH_SHORT).show();
               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           snapshot.getRef().removeValue();
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }
       });

        intent = new Intent(this ,Edit.class);
        edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               intent.putExtra("edit",x);
               startActivity(intent);
           }
       });
    }
    
}
