package com.example.chathuranga.sonitdriverapp;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chathuranga.sonitdriverapp.Parsers.DirectionsJSONParser;
import com.example.chathuranga.sonitdriverapp.Parsers.ReservationShortDetailJSONParser;
import com.example.chathuranga.sonitdriverapp.Parsers.UsesrJSONParser;
import com.example.chathuranga.sonitdriverapp.models.ReservationShort;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.chathuranga.sonitdriverapp.CommonUtilities.SERVER_URL;
import static com.example.chathuranga.sonitdriverapp.IncomingActivity.acceptFlag;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    View v;
    Button btOnline;
    Timer timer;
    int reservationID, jobNumber;

    MapView mMapView;
    private GoogleMap googleMap;
    static String pickUp;

    static double pkx, pky;

    static boolean FALG = false;
    int i = 0;
    Marker marker;

    double myLocationLat,myLocationLong;

    Polyline polyline;

    Button btStatusButton,btCancelReservation;
    LinearLayout pickupLayout,dropoffLayout;

    TextView addressText,dropofftaddress;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);


        btStatusButton = (Button) v.findViewById(R.id.btStatusButton);
        btCancelReservation = (Button) v.findViewById(R.id.btCancelReservation);
        pickupLayout= (LinearLayout) v.findViewById(R.id.pickuplayout);
        dropoffLayout= (LinearLayout) v.findViewById(R.id.dropofflayout);
        dropofftaddress = (TextView) v.findViewById(R.id.dropoffaddress);
        addressText = (TextView) v.findViewById(R.id.adressText);

        btStatusButton.setVisibility(View.INVISIBLE);
        btCancelReservation.setVisibility(View.INVISIBLE);
        pickupLayout.setVisibility(View.INVISIBLE);
        dropoffLayout.setVisibility(View.INVISIBLE);





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
                new LatLng(gps.getLatitude(), gps.getLongitude())).title("Hello Maps");
        marker.icon((BitmapDescriptorFactory.fromResource(R.drawable.ic_mycar)));
        ///googleMap.addMarker(marker);

        //LiveUpdateTimer liveUpdateTimer=  new LiveUpdateTimer();
        //liveUpdateTimer.start();
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //TODO add vehicle id
                requestAssignJob(1);


            }
        }, 10000, 20000);



        btStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(btStatusButton.getText().toString());

                if("Arrived".equals(btStatusButton.getText().toString())){
                    System.out.println("arrived");
                    btStatusButton.setText("WAITING");
                    requestData("ARRIVED");
                    btCancelReservation.setVisibility(View.VISIBLE);
                }else if("WAITING".equals(btStatusButton.getText().toString())){
                    btStatusButton.setText("START TRIP");
                    requestData("WAITING");
                    btCancelReservation.setVisibility(View.INVISIBLE);
                }else if("START TRIP".equals(btStatusButton.getText().toString())){
                    btStatusButton.setText("FINISH");
                    requestData("START TRIP");
                }else if("FINISH".equals(btStatusButton.getText().toString())){
                    btStatusButton.setText("Arrived");
                    requestData("FINISH");
                }


            }
        });



















        /////////////////////////////////////////////////////oncreate///////////////////////////////////////////////////////////////////
        return v;
    }

    private void requestData(String rideStatus) {

        String loginURL = SERVER_URL+"UpdateJobStatus.php";
        Log.e("Chahturanga      URL",loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("jobnumber", String.valueOf(jobNumber));
        p.setParam("ridestatus",rideStatus );

        Log.e("Chahturanga      URLgo", loginURL);

        LoginTask task = new LoginTask();
        task.execute(p);
    }

    private class LoginTask extends AsyncTask<RequestPackage,String,String> {
        AlertDialogManager alert = new AlertDialogManager();




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



        }


    }

















    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            myLocationLat = location.getLatitude();
            myLocationLong = location.getLongitude();

            if(marker != null){
                marker.remove();
            }

            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());


            marker = googleMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .snippet(
                            "Lat:" + location.getLatitude() + "Lng:"
                                    + location.getLongitude())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mycar))
                    .title("My Car"));
            if(googleMap != null){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };

    private void requestAssignJob(int vehicleID) {

        String loginURL = SERVER_URL + "GetAssignJob.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("vehicleID", String.valueOf(vehicleID));


        Log.e("Chahturanga      URLgo", loginURL);

        GetJobTask task = new GetJobTask();
        task.execute(p);
    }


    private class GetJobTask extends AsyncTask<RequestPackage, String, String> {
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

                pickUp = r.getPickAddress();
                reservationID = r.getReservationID();
                pkx = r.getPickupLat();
                pky = r.getPickuplong();


                System.out.println(r.getReservationID());


                //  alert.showAlertDialog(HomeActivity.this, "You Have a New Job", "Pick up =" + pickUp + "\nDrop Off = " + dropAddress, false);



                System.out.println("to job activity___________________");


                IncomingActivity incomingActivity = new IncomingActivity();


                startActivity(new Intent(getContext(), incomingActivity.getClass()));


                System.out.println("_________________________________________________________");


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {


                        System.out.println(acceptFlag);
                        if (acceptFlag == true) {
                            timer.cancel();
                            addJobdetail(reservationID);

                        }

                        if (i == 61) {
                            timer.cancel();
                        }

                        i++;


                    }
                }, 1000, 1000);


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


                if (!"0".equals(result)) {
                    jobNumber = Integer.parseInt(result.toString().trim());
                    System.out.println(jobNumber);
                    manageDriverMap();
                } else {
                    System.out.println("error");
                    //TODO add error box
                }


            }

        }
    }


    public String getPickUp() {
        return this.pickUp;
    }

    public double getPkx() {
        return this.pkx;
    }

    public double getPky() {
        return this.pky;
    }

    public static boolean isFALG() {
        return FALG;
    }

    private void manageDriverMap() {
        googleMap.clear();
        setPassengerDetails();
        btStatusButton.setVisibility(View.VISIBLE);

        addressText.setText(pickUp);
        pickupLayout.setVisibility(View.VISIBLE);
        //dropoffLayout.setVisibility(View.VISIBLE);






    }

    private void setPassengerDetails(){


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(pkx, pky))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_passengerman))
                .title("Passenger"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pkx, pky), 16.0f));
        showPath();


    }










    //drow path

    public void showPath(){
        GPSTracker gpsTracker = new GPSTracker(getActivity());


        String url = getDistanceOnRoad(pkx,pky,gpsTracker.getLatitude(),gpsTracker.getLongitude());
        DownloadTask downloadTask = new DownloadTask();

        downloadTask.execute(url);
    }

    private String getDistanceOnRoad(double latitude, double longitude,
                                     double prelatitute, double prelongitude) {
        String result_in_kms = "";
        String url = "http://maps.google.com/maps/api/directions/json?origin="
                + latitude + "," + longitude + "&destination=" + prelatitute
                + "," + prelongitude + "&sensor=false&units=metric";


        System.out.println(url);
        return url;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            DrawPath drawPathTasl = new DrawPath();

            // Invokes the thread for parsing the JSON data
            drawPathTasl.execute(result);

        }
    }

    private class DrawPath extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>>
    {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            System.out.println(jsonData[0]);
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;


            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parsers = new DirectionsJSONParser();



                // Starts parsing data
                routes = parsers.parse(jObject);
                parsers.getDistance(jObject);





            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(polyline != null){
                polyline.remove();
            }

                polyline = googleMap.addPolyline(lineOptions);



        }
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while ", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


}








