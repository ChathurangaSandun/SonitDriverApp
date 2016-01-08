package com.example.chathuranga.sonitdriverapp.Parsers;


import com.example.chathuranga.sonitdriverapp.models.ReservationAllDetals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chathuranga-Pamba on 15/11/20.
 */
public class ReservationAllDetailsJSONParser {

    public static List<ReservationAllDetals> parseFeed(String content) {

        try{
            List<ReservationAllDetals> reservationAllDetailList = new ArrayList<ReservationAllDetals>();



            JSONObject obj = new JSONObject(content);
            ReservationAllDetals  reservationAllDetals = new ReservationAllDetals();

            reservationAllDetals.setName(obj.getString("name"));
            reservationAllDetals.setTpNumber(obj.getInt("telephone"));
            reservationAllDetals.setPickUp(obj.getString("pickup"));
            reservationAllDetals.setDropOff(obj.getString("dropOff"));
            reservationAllDetals.setNote(obj.getString("note"));
            reservationAllDetals.setNoOfPassengets(obj.getInt("numOfPassengers"));

            reservationAllDetailList.add(reservationAllDetals);


            return reservationAllDetailList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
