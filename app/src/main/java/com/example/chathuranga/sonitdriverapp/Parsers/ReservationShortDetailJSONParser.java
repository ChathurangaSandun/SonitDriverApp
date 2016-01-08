package com.example.chathuranga.sonitdriverapp.Parsers;


import com.example.chathuranga.sonitdriverapp.models.ReservationShort;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pamba on 11/2/2015.
 */
public class ReservationShortDetailJSONParser {
    public static List<ReservationShort> parseFeed(String content) {


        try {

            List<ReservationShort> reservationShortList = new ArrayList<ReservationShort>();



                JSONObject obj = new JSONObject(content);
                ReservationShort  reservationShort = new ReservationShort();

                reservationShort.setReservationID(obj.getInt("reservation_id"));
                reservationShort.setPickAddress(obj.getString("pk_address"));
                reservationShort.setDropAddress(obj.getString("do_address"));


                reservationShortList.add(reservationShort);


            return reservationShortList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
