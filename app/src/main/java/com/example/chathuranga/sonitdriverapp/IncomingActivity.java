package com.example.chathuranga.sonitdriverapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class IncomingActivity extends AppCompatActivity {
    static boolean acceptFlag ;


    private GoogleMap incomMap;
    TextView tvAddress,tvTime;
    MyCountDownTimer myCountDownTimer;
    ProgressBar progressBar;

    ImageButton btCancelReservation,btAccept;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.pkMap)).getMap();
//        pickMapView.onResume();// needed to get the map to display immediately

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final HomeFragment homeFragment = new HomeFragment();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(homeFragment.getPkx(), homeFragment.getPky()), 16));
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(homeFragment.getPkx(), homeFragment.getPky())).title("A new Jpb");
        marker.icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_man)));
        googleMap.addMarker(marker);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setText(homeFragment.getPickUp());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvTime = (TextView) findViewById(R.id.tvTime);

        progressBar.setProgress(100);
        myCountDownTimer =  new MyCountDownTimer(1000*60, 1000);
        myCountDownTimer.start();


        btAccept = (ImageButton) findViewById(R.id.btAccept);
        btCancelReservation = (ImageButton) findViewById(R.id.btCancelReservation);

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(homeFragment);
                acceptFlag = true;
                finish();
            }
        });

        btCancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvTime.setText(String.valueOf(millisUntilFinished/1000)+" S");
            int progress = (int) (millisUntilFinished / 100);
            progressBar.setProgress(progress);
            if(millisUntilFinished/1000 == 1){
                finish();
            }
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    static public boolean frag(){
        return acceptFlag;

    }


}





