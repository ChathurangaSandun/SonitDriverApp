package com.example.chathuranga.sonitdriverapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverMapFragment extends Fragment implements View.OnTouchListener{

    View v;

    public DriverMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_driver_map, container, false);


        return v;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
