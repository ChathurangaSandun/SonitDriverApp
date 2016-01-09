package com.example.chathuranga.sonitdriverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OnlineActivity extends AppCompatActivity {

    Button btOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btOnline = (Button) findViewById(R.id.btOnline);


        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), LocationTrackService.class));
                Toast.makeText(getApplicationContext(), "service stop", Toast.LENGTH_LONG).show();


                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });


    }

}
