package com.example.chathuranga.sonitdriverapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chathuranga.sonitdriverapp.Parsers.ReservationShortDetailJSONParser;
import com.example.chathuranga.sonitdriverapp.models.ReservationShort;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.chathuranga.sonitdriverapp.CommonUtilities.SERVER_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    View v;
    Button btOnline;
    Timer timer;
    int reservationID,jobNumber;

    MapView mMapView;
    private GoogleMap googleMap;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapp);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately
        googleMap = mMapView.getMap();
        // latitude and longitude
        double l1 = 17.385044;
        double l2 = 78.486671;
        googleMap.setMyLocationEnabled(true);

        GPSTracker gpsTracker = new GPSTracker(getActivity());
        GPSTracker gps = new GPSTracker(getActivity());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 18));
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(gps.getLatitude(),gps.getLongitude())).title("Hello Maps");
        marker.icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_mycar)));
        googleMap.addMarker(marker);




        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                requestAssignJob(1);


            }
        }, 10000, 20000);










        return v;
    }

    private void requestAssignJob(int vehicleID) {

        String loginURL = SERVER_URL+"GetAssignJob.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("vehicleID", String.valueOf(vehicleID));


        Log.e("Chahturanga      URLgo", loginURL);

        GetJobTask task = new GetJobTask();
        task.execute(p);
    }


    private class GetJobTask extends AsyncTask<RequestPackage,String,String> {
        //AlertDialogManager alert = new AlertDialogManager();


        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            //pbLogin.setVisibility(View.VISIBLE);
            //btLogin.setEnabled(false);

        }


        @Override
        protected String doInBackground(RequestPackage... params) {

            String content = HttpManager.getData(params[0]);
            Log.e("Chahturanga    content", content);
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

            if (result.toString().trim().equals("0")) {
                Log.e("result is null", "0");


            } else {
                Log.e("Chahturanga      result", result);
                timer.cancel();


                List<ReservationShort> reservationShorts = ReservationShortDetailJSONParser.parseFeed(result);

                final ReservationShort r = reservationShorts.get(0);

                String pickUp = r.getPickAddress();
                String dropAddress = r.getDropAddress();
                reservationID = r.getReservationID();

                System.out.println(r.getReservationID());


                //  alert.showAlertDialog(HomeActivity.this, "You Have a New Job", "Pick up =" + pickUp + "\nDrop Off = " + dropAddress, false);


                //TODO direct to job activity
                System.out.println("to job activity___________________");




                /*if(){
                    addJobdetail(reservationID);
                }*/







            }
        }

        private void addJobdetail(int reservationID) {

            String loginURL = SERVER_URL + "AddJobDetails.php";
            Log.e("Chahturanga      URL", loginURL);

            RequestPackage p = new RequestPackage();
            p.setMethod("GET");
            p.setUri(loginURL);
            p.setParam("reservationID", String.valueOf(reservationID));


            Log.e("Chahturanga      URLgo", loginURL);

            AddJobTask task = new AddJobTask();
            task.execute(p);
        }

        private class AddJobTask extends AsyncTask<RequestPackage, String, String> {
            //AlertDialogManager alert = new AlertDialogManager();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //pbLogin.setVisibility(View.VISIBLE);
                //btLogin.setEnabled(false);

            }


            @Override
            protected String doInBackground(RequestPackage... params) {

                String content = HttpManager.getData(params[0]);
                Log.e("Chahturanga    content", content);
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


                if(!"0".equals(result)){
                    jobNumber = Integer.parseInt(result.toString().trim());
                }else{
                    System.out.println("error");
                    //TODO add error box
                }



            }

        }
    }


}
