package com.example.marwa.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class details extends AppCompatActivity {

    private static final String TAGh ="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = new Intent();
        intent.getStringExtra("holder");
        Log.i(TAGh, intent.toString());
    }
}
