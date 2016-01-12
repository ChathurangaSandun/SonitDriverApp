package com.example.chathuranga.sonitdriverapp.Parsers;

import com.example.chathuranga.sonitdriverapp.models.Driver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chathuranga on 1/11/2016.
 */
public class DriverJSONParser {

    public static List<Driver> parseFeed(String content) {


        try {
            JSONArray ar = new JSONArray(content);
            List<Driver> userList = new ArrayList<Driver>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Driver driver = new Driver();

                driver.setUserName(obj.getString("user_name"));
                driver.setUserType(obj.getString("user_type"));
                driver.setDriverID(obj.getInt("driver_id"));


                userList.add(driver);
            }

            return userList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}