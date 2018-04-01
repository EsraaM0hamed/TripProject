package com.example.marwa.androidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    Boolean flag;
    TextView name;
    TextView  note;
    TextView type;
    TextView date;
    TextView start;
    TextView end;
    Button delete;
    Button edit;
    Intent intent ;
    Intent doneintent ;
    CheckBox done;
    ArrayList<String> DoneList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       final String x= getIntent().getStringExtra("holder");
       Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
        DoneList = new ArrayList<>();
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
         done=findViewById(R.id.done);
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
                    if(tr.getFlag()!=null) {
                        if (tr.getFlag() == true) {
                            done.setChecked(true);
                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(details.this); //Read Update
               alertDialog.setMessage("Are you sure you want to Delete");
               alertDialog.setCancelable(false);
               alertDialog.setPositiveButton("Yes",
                              new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog,int id) {
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
                               //details.this.finish();
                           }
                       }).setNegativeButton("No",new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog,int id) {
                       // if this button is clicked, just close
                       // the dialog box and do nothing
                       dialog.cancel();
                   }
               }).create().show();
           }

       });

        intent = new Intent(this ,Edit.class);
        doneintent=new Intent(this,Main2Activity.class);

        edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               intent.putExtra("edit",x);
               startActivity(intent);
           }
       });
        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    flag=true;
                    DoneList.add(name.getText().toString());
                    tr = new Trip();
                    tr.setTripName(name.getText().toString());
                    tr.setTripType(type.getText().toString());
                    tr.setNotes(note.getText().toString());
                    tr.setStartPoint(start.getText().toString());
                    tr.setEndPoint(end.getText().toString());
                    tr.setDate(date.getText().toString());
                    tr.setFlag(true);
                    ref.child(name.getText().toString()).setValue(tr);
                }else{
                    flag=false;
                    DoneList.add(name.getText().toString());
                    tr = new Trip();
                    tr.setTripName(name.getText().toString());
                    tr.setTripType(type.getText().toString());
                    tr.setNotes(note.getText().toString());
                    tr.setStartPoint(start.getText().toString());
                    tr.setEndPoint(end.getText().toString());
                    tr.setDate(date.getText().toString());
                    tr.setFlag(false);
                    ref.child(name.getText().toString()).setValue(tr);

                }
            }
        });
    }
    
}