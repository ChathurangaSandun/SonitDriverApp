package com.example.chathuranga.sonitdriverapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chathuranga.sonitdriverapp.Parsers.DriverJSONParser;
import com.example.chathuranga.sonitdriverapp.models.Driver;

import java.util.ArrayList;
import java.util.List;

import static com.example.chathuranga.sonitdriverapp.CommonUtilities.SERVER_URL;


public class LoginActivity extends AppCompatActivity {

    //paramers

    private EditText etUsername,etPassword;
    private Button btLogin,btCall,btRegister;
    private ProgressBar pbLogin;
    private String username;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;

    List<Driver> drivers = new ArrayList<>();

    Driver logindriver;
    static int vehicleID;


    //session
    SessionManager session;

    public static int getVehicleIDFromDriver() {//vehicle id
        return vehicleID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponets();

        // Session Manager
        session = new SessionManager(getApplicationContext());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "0718256773";
                myIntent.setData(Uri.parse(phNum));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                startActivity(myIntent);
            }


        });
    }

    private void initComponets(){
        etUsername = (EditText) findViewById(R.id.etUsername );
        etPassword = (EditText) findViewById(R.id.etPassword);
        //btCall = (Button) findViewById(R.id.btCall);
        btLogin = (Button)findViewById(R.id.btLogin);
        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);


        pbLogin.setVisibility(View.INVISIBLE);



        /*
            login button
         */


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check connection
                cd = new ConnectionDetector(getApplicationContext());

                Log.e("Tag", String.valueOf(cd.isConnectingToInternet()));

                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    alert.showAlertDialog(LoginActivity.this,
                            "Internet Connection Error",
                            "Please connect to working Internet connection", false);
                    // stop executing code by return
                    return;
                }


                username = etUsername.getText().toString();
                String password = etPassword.getText().toString();


                if (username.trim().length() > 0 && password.trim().length() > 0) {


                    requestData(username, password);
                } else {
                    alert.showAlertDialog(LoginActivity.this, "Login Error!", "Please enter your details", false);
                }

            }
        });

    }



    private void requestData(String userName,String password) {

        String loginURL = SERVER_URL+"DriverLogin.php";
        Log.e("Chahturanga      URL",loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("username", userName);
        p.setParam("password", password);

        Log.e("Chahturanga      URLgo", loginURL);

        LoginTask task = new LoginTask();
        task.execute(p);
    }

    private class LoginTask extends AsyncTask<RequestPackage,String,String> {
        AlertDialogManager alert = new AlertDialogManager();




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLogin.setVisibility(View.VISIBLE);
            btLogin.setEnabled(false);

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


            drivers = DriverJSONParser.parseFeed(result);
            Log.e("Chahturanga      size()", String.valueOf(drivers.size()));


            if (drivers.size() == 1) {
                logindriver = drivers.get(0);

                if (logindriver.getUserType().equals("driver")) {
                    // Creating user login session
                    // For testing i am stroing name
                    // Use user real data



                    getVehicleID();

                    Intent homeActivity = new Intent(getApplicationContext(), OnlineActivity.class);
                    startActivity(homeActivity);
                } else {
                    alert.showAlertDialog(LoginActivity.this, "Login Error!", "Invalied Username and Password", false);
                    etUsername.setText("");
                    etPassword.setText("");
                }
            } else {
                alert.showAlertDialog(LoginActivity.this, "Login Error", "Invalied Username and Password", false);
                etUsername.setText("");
                etPassword.setText("");
            }




            pbLogin.setVisibility(View.INVISIBLE);
            btLogin.setEnabled(true);
        }


    }

    private void getVehicleID() {

        String loginURL = SERVER_URL+"GetVehicleID.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("driverid",String.valueOf(logindriver.getDriverID() ));


        Log.e("Chahturanga      URLgo", loginURL);

        GetVehicleTask task = new GetVehicleTask();
        task.execute(p);
    }

    private class GetVehicleTask extends AsyncTask<RequestPackage,String,String> {





        @Override
        protected void onPreExecute() {
            super.onPreExecute();


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
            vehicleID = Integer.parseInt(result.replace("\"","").trim());
            session.createLoginSession(username,String.valueOf(vehicleID));





        }

    }







}

