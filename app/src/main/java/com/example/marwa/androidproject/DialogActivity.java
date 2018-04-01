package com.example.marwa.androidproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by esraa on 3/31/18.
 */

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    Button later_btn, start_btn,cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        start_btn = (Button) findViewById(R.id.start_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        later_btn = (Button) findViewById(R.id.later_btn);
        start_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_btn:

               // showToastMessage("Ok Button Clicked");
                this.finish();

                break;

            case R.id.later_btn:

                //showToastMessage("Cancel Button Clicked");
                this.finish();

                break;
        }


    }
}
