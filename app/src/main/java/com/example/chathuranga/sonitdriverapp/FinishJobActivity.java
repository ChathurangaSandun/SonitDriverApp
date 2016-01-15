package com.example.chathuranga.sonitdriverapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.chathuranga.sonitdriverapp.CommonUtilities.SERVER_URL;

public class FinishJobActivity extends AppCompatActivity {


    Button btgoOffline,btSubmit;
    TextView tvFare;
    String jobNumber,vehicleID;
    double fareDouble;
    String fare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

            jobNumber = extras.getString("JOBNUMBER");
            vehicleID = extras.getString("VEHICLEID");





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tvFare = (TextView) findViewById(R.id.tvFare);
        btgoOffline = (Button) findViewById(R.id.goOnlineButton);
        btSubmit = (Button) findViewById(R.id.btSubmitFare);






        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fare= tvFare.getText().toString();
                    fareDouble = Double.parseDouble(fare);
                System.out.println(fareDouble);
                System.out.println(vehicleID);
                System.out.println(jobNumber);
                    finishJob(fareDouble);



            }
        });

        btgoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), OnlineActivity.class));

            }
        });


    }

    private void finishJob(double fare) {

        String loginURL = SERVER_URL+"FinishJob.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("vehicleid", vehicleID);
        p.setParam("jobnumber", jobNumber);
        p.setParam("fare", String.valueOf(fare));

        Log.e("Chahturanga      URLgo", loginURL);

        FinishJobTask task = new FinishJobTask();
        task.execute(p);
    }

    private class FinishJobTask extends AsyncTask<RequestPackage,String,String> {





        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           ;

        }



        @Override
        protected String doInBackground(RequestPackage... params) {

            String content = HttpManager.getData(params[0]);
            Log.e("Chahturanga    content",content);
            return content;
        }



        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("Chahturanga      result", result);




        }


    }

}
